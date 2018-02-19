package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.ui.stage.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML private ImageView imgv1;

    @FXML private ImageView imgv2;

    @FXML private ImageView imgv3dots;

    @FXML private ImageView imgvbubbles;

    @FXML private ImageView imgvnext;

    @FXML private ImageView imgvprev;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgv1.setImage(new Image(getClass().getResource("/image/img1.png").toString()));
        imgv2.setImage(new Image(getClass().getResource("/image/img2.png").toString()));
        imgv3dots.setImage(new Image(getClass().getResource("/image/img3dots.png").toString()));
        imgvbubbles.setImage(new Image(getClass().getResource("/image/img_bubbles.gif").toString()));
        imgvnext.setImage(new Image(getClass().getResource("/image/img_next.png").toString()));
        imgvprev.setImage(new Image(getClass().getResource("/image/img_prev.png").toString()));
    }

    @FXML
    public void onActionSend(ActionEvent event) {
        WelcomeStage stage = ((WelcomeStage) ((Node) event.getSource()).getScene().getWindow());
        stage.hide();
        stage.getOnWelcomeClose().onClose(false);
    }

    @FXML
    public void onActionReceive(ActionEvent event) {
        WelcomeStage stage = ((WelcomeStage) ((Node) event.getSource()).getScene().getWindow());
        stage.hide();
        stage.getOnWelcomeClose().onClose(true);
    }
}
