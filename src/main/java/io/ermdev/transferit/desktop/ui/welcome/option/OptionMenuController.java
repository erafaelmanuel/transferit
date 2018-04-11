package io.ermdev.transferit.desktop.ui.welcome.option;

import io.ermdev.transferit.desktop.ui.option.SettingStage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionMenuController implements Initializable {

    @FXML
    ImageView imgvsettings;

    @FXML
    ImageView imgvhelp;

    @FXML
    ImageView imgvabout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final String IMAGE_SETTINGS = "/image/system/settings.png";
        final String IMAGE_HELP = "/image/system/help.png";
        final String IMAGE_ABOUT = "/image/system/about.png";

        imgvsettings.setImage(new Image(getClass().getResource(IMAGE_SETTINGS).toExternalForm()));
        imgvhelp.setImage(new Image(getClass().getResource(IMAGE_HELP).toExternalForm()));
        imgvabout.setImage(new Image(getClass().getResource(IMAGE_ABOUT).toExternalForm()));
    }

    @FXML
    void onSettings() {
        final SettingStage settingStage = new SettingStage();
        settingStage.display(0, 0);
    }
}
