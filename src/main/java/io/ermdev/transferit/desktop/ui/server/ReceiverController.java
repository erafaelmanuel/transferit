package io.ermdev.transferit.desktop.ui.server;

import io.ermdev.transferit.arch.Book;
import io.ermdev.transferit.arch.Subscriber;
import io.ermdev.transferit.desktop.component.ItemBox;
import io.ermdev.transferit.desktop.ui.welcome.WelcomeStage;
import io.ermdev.transferit.desktop.util.MasterConfig;
import io.ermdev.transferit.core.*;
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
import java.util.ResourceBundle;

public class ReceiverController implements ServerListener, Subscriber, Initializable, InvitationDialogListener {

    private InvitationDialogStage invitationDialogStage;

    private ItemServer itemServer;

    private LinkServer linkServer;

    private Thread summoner;

    private Endpoint endpoint;

    final private MasterConfig masterConfig = new MasterConfig();

    private List<Item> items = new ArrayList<>();

    @FXML VBox container;

    @FXML Label lblStatus;

    @FXML StackPane browser;

    @FXML Label lblBrowser;

    @FXML Button btnClear;

    @FXML Button btnCancel;

    public ReceiverController() {
        invitationDialogStage = new InvitationDialogStage(this);
        itemServer = new ItemServer(masterConfig.getDirOrDefault());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblBrowser.setText("0 Receive file(s)");
        container.getChildren().clear();
        container.getChildren().add(browser);

        endpoint = new Endpoint(masterConfig.getPortOrDefault());
        endpoint.subscribe(this);

        linkServer = new LinkServer(endpoint);
        linkServer.setServerListener(this);
        linkServer.open();
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
                linkServer.accept();
                lblStatus.setText("Connected");
                lblStatus.setStyle("-fx-background-color: #00b894");
            } else {
                linkServer.reject();
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

    @FXML void onCancel(ActionEvent event) {
        lblBrowser.setText("0 Receive file(s)");
        container.getChildren().clear();
        container.getChildren().add(browser);

        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();

        linkServer.stop();
        linkServer = null;

        WelcomeStage welcomeStage = new WelcomeStage();
        welcomeStage.display(stage.getX(), stage.getY());
    }

    @FXML
    void onClear() {
        lblBrowser.setText("0 Receive file(s)");
        container.getChildren().clear();
        container.getChildren().add(browser);
    }
}
