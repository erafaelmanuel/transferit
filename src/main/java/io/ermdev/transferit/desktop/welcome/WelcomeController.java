package io.ermdev.transferit.desktop.welcome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    private ImageView imgv;

    @FXML
    private ImageView imgv3dots;

    @FXML
    private ImageView imgvnext;

    @FXML
    private ImageView imgvprev;

    private String images[] = new String[5];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        images[0] = "/image/wlcm-slide/img1.png";
        imgv.setImage(new Image(getClass().getResource(images[0]).toString()));
        imgv3dots.setImage(new Image(getClass().getResource("/image/system/img3dots.png").toString()));
        imgvnext.setImage(new Image(getClass().getResource("/image/system/img_next.png").toString()));
        imgvprev.setImage(new Image(getClass().getResource("/image/system/img_prev.png").toString()));
    }

    @FXML
    public void onSend(ActionEvent event) {
        WelcomeStage stage = ((WelcomeStage) ((Node) event.getSource()).getScene().getWindow());
        stage.hide();
        stage.getOnWelcomeClose().onClose(false);
    }

    @FXML
    public void onReceive(ActionEvent event) {
        WelcomeStage stage = ((WelcomeStage) ((Node) event.getSource()).getScene().getWindow());
        stage.hide();
        stage.getOnWelcomeClose().onClose(true);
    }
}
