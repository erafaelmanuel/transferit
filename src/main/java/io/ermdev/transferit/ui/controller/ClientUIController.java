package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.local.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;

public class ClientUIController {

    private Client client;

    @FXML
    private TextField txHost;
    @FXML
    private Button btnCDC;

    @FXML
    public void onActionConnect(ActionEvent event) {
        try {
            client = new Client(txHost.getText(), 23411);
            if (btnCDC.getText().equals("Connect")) {
                btnCDC.setText("Disconnect");
            } else {
                btnCDC.setText("Connect");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to connect.");
        }
    }
}
