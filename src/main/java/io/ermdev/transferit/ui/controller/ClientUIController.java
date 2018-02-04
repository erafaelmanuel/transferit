package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.local.client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientUIController {

    private Client client;

    @FXML
    private TextField txHost;
    @FXML
    private Button btnCDC;

    @FXML
    public void onActionConnect(ActionEvent event) {
        try {
            if(btnCDC.getText().equalsIgnoreCase("Connect")) {
                if (txHost.getText().trim().isEmpty()) {
                    String host = "localhost";
                    client = new Client(host);
                } else {
                    String host = txHost.getText().trim();
                    client = new Client(host);
                }
                btnCDC.setText("Disconnect");
            } else {
                client.getSocket().close();
                btnCDC.setText("Connect");
            }
        } catch (Exception e) {
            Platform.runLater(()->btnCDC.setText("Connect"));
        }
    }
}
