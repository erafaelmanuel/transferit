package io.ermdev.transferit.desktop.server;

import io.ermdev.transferit.integration.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MobServerController implements ServerListener, Subscriber, Initializable {

    private LinkServer server;

    private Thread summoner;

    private Endpoint endpoint;

    @FXML
    VBox container;

    @FXML
    Label lblStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        endpoint = new Endpoint(23411);
        endpoint.subscribe(this);
        server = new LinkServer(endpoint);
        server.setServerListener(this);
        server.open();
    }

    @FXML
    void onBrowse() {

    }

    @FXML
    void onDrag(DragEvent event) {

    }

    @FXML
    void onDrop(DragEvent event) {

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
    public void onStop() {

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
}
