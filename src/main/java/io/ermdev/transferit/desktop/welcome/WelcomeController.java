package io.ermdev.transferit.desktop.welcome;

import io.ermdev.transferit.desktop.component.Cover;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    private ImageView imgv3dots;

    @FXML
    private ImageView imgvnext;

    @FXML
    private ImageView imgvprev;

    @FXML AnchorPane container;

    private Cover covers[] = new Cover[5];

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        covers[0] = new Cover1();
        covers[1] = new Cover2();
        covers[2] = new Cover3();
        covers[3] = new Cover4();

        container.getChildren().add(0, covers[(int)((Math.random() * 4))]);

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
