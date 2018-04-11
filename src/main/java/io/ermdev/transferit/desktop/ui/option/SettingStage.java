package io.ermdev.transferit.desktop.ui.option;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;

import java.net.URL;

final public class SettingStage extends BaseStage {

    public SettingStage() {
        try {
            final URL fxml = classLoader.getResource("fxml/settings.fxml");
            final URL style = classLoader.getResource("css/settings-style.css");
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 302, 233);
            if (style != null) {
                scene.getStylesheets().add(style.toExternalForm());
            }
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Settings");
            setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
