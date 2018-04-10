package io.ermdev.transferit.desktop.server;

import io.ermdev.transferit.integration.*;
import io.ermdev.transferit.desktop.component.ItemBox;
import io.ermdev.transferit.desktop.welcome.WelcomeInteract;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class MobServerController implements ServerListener, Subscriber, Initializable, InvitationDialogListener {

    private InvitationDialogStage invitationDialogStage;

    private ItemServer itemServer;

    private int port;

    private WelcomeInteract wi;

    private LinkServer server;

    private Thread summoner;

    private Endpoint endpoint;


    private List<Item> items = new ArrayList<>();

    @FXML
    VBox container;

    @FXML
    Label lblStatus;

    @FXML
    StackPane browser;

    @FXML
    Label lblBrowser;

    @FXML
    Button btnClear;

    @FXML
    Button btnCancel;

    public MobServerController() {
        final ClassLoader classLoader = getClass().getClassLoader();
        final Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("config/application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        invitationDialogStage = new InvitationDialogStage(this);

        itemServer = new ItemServer(properties.getProperty("app.dir", "files"));

        port = Integer.parseInt(properties.getProperty("app.port", "0"));
    }

    public void reset() {
        lblBrowser.setText("0 Receive file(s)");
        container.getChildren().clear();
        container.getChildren().add(browser);
    }

    public void setWelcomeInteract(WelcomeInteract wi) {
        this.wi = wi;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();
        endpoint = new Endpoint(port);
        endpoint.subscribe(this);
        server = new LinkServer(endpoint);
        server.setServerListener(this);
        server.open();
    }

    @Override
    public void onInvite() {
        summoner = new Thread(() ->
                Platform.runLater(() -> {
                    invitationDialogStage.display();
                    invitationDialogStage.getController()
                            .setLabelText("You want to accept " + endpoint.getHost() + "?");
                    summoner = null;
                }));
        summoner.start();
    }

    @Override
    public void onChoose(boolean accept) {
        Platform.runLater(() -> {
            if (accept) {
                server.accept();
                lblStatus.setText("Connected");
                lblStatus.setStyle("-fx-background-color: #00b894");
            } else {
                server.reject();
            }
        });
    }

    @Override
    public void onNewFile(String name, long size) {
        if (items.size() < 1) {
            Platform.runLater(() -> container.getChildren().clear());
        }
        Item item = new Item(new File(name));
        item.setSize((double) size);
        items.add(item);
        Platform.runLater(() -> container.getChildren().add(new ItemBox(item)));
    }

    @Override
    public void onReceiveFile(InputStream inputStream) {
        try {
            Platform.runLater(() -> {
                btnClear.setDisable(true);
                btnCancel.setDisable(true);
            });
            itemServer.setItems(items);
            itemServer.receiveFile(inputStream);
            Platform.runLater(() -> {
                btnClear.setDisable(false);
                btnCancel.setDisable(false);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void release(Book<?> book) {
        if ((Boolean) book.getContent()) {
            Platform.runLater(() -> {
                lblStatus.setText("Connected");
                lblStatus.setStyle("-fx-background-color: #00b894");
            });
        } else {
            Platform.runLater(() -> {
                if (invitationDialogStage.isShowing()) {
                    invitationDialogStage.hide();
                } else {
                    lblStatus.setText("Disconnected");
                    lblStatus.setStyle("-fx-background-color: #d63031");
                }
            });
        }
    }

    @FXML
    void onCancel(ActionEvent event) {
        reset();
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();

        server.stop();
        server = null;

        if (wi != null) {
            wi.setDisplay(stage.getX(), stage.getY());
        }
    }

    @FXML
    void onClear() {
        reset();
    }
}
