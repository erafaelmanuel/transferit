package io.ermdev.transferit.desktop.welcome;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionMenuController implements Initializable {

    @FXML
    ImageView imgvSettings;

    @FXML
    ImageView imgvHelp;

    @FXML
    ImageView imgvAbout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgvSettings.setImage(new Image(getClass().getResource("/image/system/settings.png").toExternalForm()));
        imgvHelp.setImage(new Image(getClass().getResource("/image/system/help.png").toExternalForm()));
        imgvAbout.setImage(new Image(getClass().getResource("/image/system/about.png").toExternalForm()));
    }
}
