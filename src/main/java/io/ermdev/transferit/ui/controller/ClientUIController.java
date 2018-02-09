package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.local.client.BasicClient;
import io.ermdev.transferit.local.client.Receiver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClientUIController {

    private BasicClient client;
    private final List<File> FILES = new ArrayList<>();

    @FXML
    private TextField txHost;
    @FXML
    private Button btnCDC;

    @FXML
    public void onActionConnect() {
        try {
            Receiver receiver = new Receiver();
            receiver.setHost(txHost.getText());
            receiver.setPort(23411);

            client = new BasicClient(receiver);
            if (btnCDC.getText().equals("Connect")) {
                btnCDC.setText("Disconnect");
            } else {
                btnCDC.setText("Connect");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to connect.");
        }
    }

    @FXML
    public void onActionSend() {
        Thread thread = new Thread(()->{
            if (client != null && FILES.size() > 0) {
                for (File file : FILES) {
                    try {
                        client.openTransaction(file);
                        System.out.println(file.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                FILES.clear();
            }
        });
        thread.start();
    }

    @FXML
    public void onActionBrowse() {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(null);
        if (files != null && files.size() > 0) {
            FILES.addAll(files);
        }
    }
}
