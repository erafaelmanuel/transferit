package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.local.client.BasicClient;
import io.ermdev.transferit.local.client.Receiver;
import io.ermdev.transferit.util.Subscriber;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientUIController implements Subscriber {

    private BasicClient client;
    private List<File> files = new ArrayList<>();
    private Receiver receiver = new Receiver();

    @FXML
    private TextField txHost;
    @FXML
    private Button btnCDC;

    @FXML
    public void onActionConnect() {
        try {
            if (btnCDC.getText().equals("Connect")) {
                receiver.subscribe(this);
                receiver.setHost(txHost.getText());
                receiver.setPort(23411);
                receiver.setConnected(true);
                client = new BasicClient(receiver);
            } else {
                receiver.setConnected(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to connect.");
        }
    }

    @FXML
    public void onActionSend() {
        Thread thread = new Thread(() -> {
            if (client != null && files.size() > 0) {
                for (File file : files) {
                    try {
                        client.openTransaction(file);
                        System.out.println(file.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                        files.clear();
                        break;
                    }
                }
                files.clear();
            }
        });
        thread.start();
    }

    @FXML
    public void onActionBrowse() {
        FileChooser fileChooser = new FileChooser();
        List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        if (newFiles != null && newFiles.size() > 0) {
            files.addAll(newFiles);
        }

    }

    @Override
    public void update(boolean status) {
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                if (status) {
                    btnCDC.setText("Disconnect");
                } else {
                    btnCDC.setText("Connect");
                }
            });
        });
        thread.start();
    }
}
