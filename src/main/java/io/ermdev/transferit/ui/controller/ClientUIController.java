package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.local.client.BasicClient;
import io.ermdev.transferit.local.client.Client;
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
            if(client != null) {
                client.getSocket().close();
            }
            client = new BasicClient(txHost.getText(), 23411);

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
        if (client != null && FILES.size() > 0) {
            for (File file : FILES) {
                try {
                    client.send(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            FILES.clear();
        }
        btnCDC.setText("Connect");
    }

    @FXML
    public void onActionBrowse() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            FILES.add(file);
            System.out.println(file.getName());
        }
    }
}
