package io.ermdev.transferit.desktop.welcome;

import io.ermdev.transferit.desktop.component.Cover;
import io.ermdev.transferit.desktop.cover.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    private final OptionMenuStage optionMenu = new OptionMenuStage();

    private final Cover covers[] = new Cover[5];

    private WelcomeInteract.WelcomeListener wc;

    @FXML ImageView option;

    @FXML AnchorPane container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final String CSS = "/css/option-menu-style.css";
        final String IMAGE_OPTION = "/image/system/more.png";
        final int GENERATED_NUMBER = (int) ((Math.random() * 5));

        covers[0] = new Cover1();
        covers[1] = new Cover2();
        covers[2] = new Cover3();
        covers[3] = new Cover4();
        covers[4] = new Cover5();

        container.getChildren().add(0, covers[GENERATED_NUMBER]);
        option.setImage(new Image(getClass().getResource(IMAGE_OPTION).toString()));
        optionMenu.getScene().getStylesheets().add(getClass().getResource(CSS).toExternalForm());
    }

    public void setWelcomeListener(WelcomeInteract.WelcomeListener wc) {
        this.wc = wc;
    }

    @FXML void onSend(ActionEvent event) {
        wc.onSelectSend();
    }

    @FXML void onReceive(ActionEvent event) {
        wc.onSelectReceive();
    }


    @FXML void onOption(MouseEvent event) {
        if (!optionMenu.isDisplayed()) {
            optionMenu.setX(event.getScreenX());
            optionMenu.setY(event.getScreenY());
            optionMenu.display();
        } else {
            optionMenu.hide();
            optionMenu.setDisplay(false);
        }
    }
}
