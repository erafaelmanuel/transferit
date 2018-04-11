package io.ermdev.transferit.desktop.ui.option;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

final public class SettingStage extends Stage {

    private SettingController sdc;

    public SettingStage() {
        try {
            final String FXML = "/fxml/settings.fxml";
            //final String CSS = "/css/settings-style.css";
            final FXMLLoader loader = new FXMLLoader();
            final URL location = getClass().getResource(FXML);
            //final String style = getClass().getResource(CSS).toExternalForm();
            loader.setLocation(location);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 250, 233);
            //scene.getStylesheets().add(style);

            initModality(Modality.APPLICATION_MODAL);
            setTitle("Transferit v1.0");
            setScene(scene);
            setController(loader.getController());
            setOnCloseRequest(e -> System.exit(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        setResizable(false);
        sizeToScene();
        show();
    }

    public SettingController getController() {
        return sdc;
    }

    public void setController(SettingController sdc) {
        this.sdc = sdc;
    }
}
