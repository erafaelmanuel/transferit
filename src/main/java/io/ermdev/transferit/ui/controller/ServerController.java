package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.Endpoint;
import io.ermdev.transferit.TcpServer;
import io.ermdev.transferit.fun.ServerListener;
import io.ermdev.transferit.util.Subscriber;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Subscriber, Initializable, ServerListener {

    private TcpServer server;

    @Override
    public void update(boolean status) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Endpoint endpoint = new Endpoint();
        endpoint.setPort(23411);
        endpoint.subscribe(this);

        server = new TcpServer(endpoint);
        server.setServerListener(this);
        server.findConnection();
    }

    @Override
    public void onInvite() {
        Thread thread = new Thread(() ->
                Platform.runLater(() -> {
                    boolean isAccepted = (JOptionPane.showConfirmDialog(null, "You want to accept?", null,
                            JOptionPane.YES_NO_OPTION) == 0);
                    if(isAccepted) {
                        server.accept();
                    } else {
                        server.reject();
                        server.findConnection();
                    }
                })
        );
        thread.start();
    }

    @Override
    public void onStop() {
        server.findConnection();
    }

    @FXML
    protected void onActionClose() {
        server.stop();
        server.findConnection();
    }
}