package io.ermdev.transferit.desktop.server;

import io.ermdev.transferit.desktop.component.MyBox;
import io.ermdev.transferit.desktop.welcome.WelcomeStage;
import io.ermdev.transferit.integration.*;
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

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MobServerController implements ServerListener, Subscriber, Initializable {

    private WelcomeStage welcomeStage;

    private LinkServer server;

    private Thread summoner;

    private Endpoint endpoint;

    private ItemServer itemServer = new ItemServer();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
        endpoint = new Endpoint(23411);
        endpoint.subscribe(this);
        server = new LinkServer(endpoint);
        server.setServerListener(this);
        server.open();
    }

    public void initialize() {
        lblBrowser.setText("0 Receive file(s)");
        container.getChildren().clear();
        container.getChildren().add(browser);
    }

    public void setWelcomeStage(WelcomeStage welcomeStage) {
        this.welcomeStage = welcomeStage;
    }

    @Override
    public void onInvite() {
        summoner = new Thread(() -> {
            Platform.runLater(() -> {
                boolean isAccepted = (JOptionPane.showConfirmDialog(null, "You want to accept "
                                + endpoint.getHost() + " ?", null,
                        JOptionPane.YES_NO_OPTION) == 0);
                if (isAccepted) {
                    server.accept();
                    lblStatus.setText("Connected");
                    lblStatus.setStyle("-fx-background-color: #00b894");
                } else {
                    server.stop();
                }
            });
            summoner = null;
        });
        summoner.start();
    }

    @Override
    public void onNewFile(String name, long size) {
        if (items.size() < 1) {
            Platform.runLater(() -> container.getChildren().clear());
        }
        Item item = new Item(new File(name));
        item.setSize((double) size);
        items.add(item);
        Platform.runLater(() -> container.getChildren().add(new MyBox(item)));
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
                lblStatus.setText("Disconnected");
                lblStatus.setStyle("-fx-background-color: #d63031");
            });
        }
    }

    @FXML
    void onCancel(ActionEvent event) {
        initialize();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        server.stop();
        server = null;
        if (welcomeStage != null) {
            welcomeStage.show();
        }
    }

    @FXML
    void onClear() {
        initialize();
    }
}
