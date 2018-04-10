package io.ermdev.transferit.desktop.ui.welcome;

import io.ermdev.transferit.desktop.component.Cover;
import io.ermdev.transferit.desktop.cover.*;
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

    private WelcomeInteract welcomeInteract;

    @FXML
    ImageView option;

    @FXML
    AnchorPane container;

    public void setWelcomeInteract(WelcomeInteract welcomeInteract) {
        this.welcomeInteract = welcomeInteract;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL style = classLoader.getResource("css/option-menu-style.css");
        final URL image = classLoader.getResource("image/system/more.png");
        final int GENERATED_NUMBER = (int) ((Math.random() * 5));

        covers[0] = new Cover1();
        covers[1] = new Cover2();
        covers[2] = new Cover3();
        covers[3] = new Cover4();
        covers[4] = new Cover5();

        container.getChildren().add(0, covers[GENERATED_NUMBER]);
        if (image != null) {
            option.setImage(new Image(image.toString()));
        }
        if (style != null) {
            optionMenu.getScene().getStylesheets().add(style.toExternalForm());
        }
    }

    @FXML
    void onSend() {
        if (welcomeInteract != null) {
            welcomeInteract.selectSend();
        }
    }

    @FXML
    void onReceive() {
        if (welcomeInteract != null) {
            welcomeInteract.selectReceive();
        }
    }

    @FXML
    void onOption(MouseEvent event) {
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
