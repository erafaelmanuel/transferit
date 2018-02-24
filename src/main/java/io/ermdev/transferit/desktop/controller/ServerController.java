package io.ermdev.transferit.desktop.controller;

import io.ermdev.transferit.integration.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Subscriber, Initializable, ServerListener {

    private LinkServer server;

    private ServerListener serverListener;

    @Override
    public void release(Book book) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Endpoint endpoint = new Endpoint(23411);
        endpoint.subscribe(this);

//        server = new TcpServer(endpoint);
//        server.addListener(this);
//        server.findConnection();
        server = new LinkServer(endpoint);
        server.setServerListener(this);
        server.open();
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
                        server.close();
                    }
                })
        );
        thread.start();
    }

    @Override
    public void onStop() {
//        Endpoint endpoint = new Endpoint(23411);
//        endpoint.subscribe(this);
//
//        server = new TcpServer(endpoint);
//        server.addListener(this);
//        server.findConnection();
    }

    @FXML
    protected void onActionClose() {
//        server.stop();
//
//        Endpoint endpoint = new Endpoint(23411);
//        endpoint.subscribe(this);
//
//        server = new TcpServer(endpoint);
//        server.addListener(this);
//        server.findConnection();
    }
}
