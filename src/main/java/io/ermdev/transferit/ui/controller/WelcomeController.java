package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.ui.stage.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class WelcomeController {

    @FXML
    public void onActionSend(ActionEvent event) {
        WelcomeStage stage = ((WelcomeStage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
        stage.getOnWelcomeClose().onClose(false);
    }

    @FXML
    public void onActionReceive(ActionEvent event) {
        WelcomeStage stage = ((WelcomeStage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
        stage.getOnWelcomeClose().onClose(true);
    }
}
