package io.ermdev.transferit.desktop.welcome;

import io.ermdev.transferit.desktop.component.Cover;
import io.ermdev.transferit.desktop.component.cover.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML ImageView imgv3dots;

    @FXML ImageView imgvnext;

    @FXML ImageView imgvprev;

    @FXML AnchorPane container;

    private final OptionMenu optionMenu = new OptionMenu();

    private Cover covers[] = new Cover[5];

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        covers[0] = new Cover1();
        covers[1] = new Cover2();
        covers[2] = new Cover3();
        covers[3] = new Cover4();
        covers[4] = new Cover5();

        container.getChildren().add(0, covers[(int) ((Math.random() * 5))]);
        imgv3dots.setImage(new Image(getClass().getResource("/image/system/more.png").toString()));
        imgvnext.setImage(new Image(getClass().getResource("/image/system/img_next.png").toString()));
        imgvprev.setImage(new Image(getClass().getResource("/image/system/img_prev.png").toString()));

        optionMenu.getScene().getStylesheets()
                .add(getClass().getResource("/css/option-menu-style.css").toExternalForm());
    }

    @FXML
    void onSend(ActionEvent event) {
        WelcomeStage stage = ((WelcomeStage) ((Node) event.getSource()).getScene().getWindow());
        stage.hide();
        stage.getWelcomeListener().onClose(false);
    }

    @FXML
    void onReceive(ActionEvent event) {
        WelcomeStage stage = ((WelcomeStage) ((Node) event.getSource()).getScene().getWindow());
        stage.hide();
        stage.getWelcomeListener().onClose(true);
    }


    @FXML
    void onOption(MouseEvent me) {
        if (!optionMenu.isDisplayed()) {
            optionMenu.setX(me.getScreenX());
            optionMenu.setY(me.getScreenY());
            optionMenu.display();
        } else {
            optionMenu.hide();
            optionMenu.setDisplay(false);
        }
    }

}
