package io.ermdev.transferit.ui.stage;

import io.ermdev.transferit.ui.controller.MainUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainStage extends Stage {

    private MainUIController mainUIController;

    public MainStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = this.getClass().getResource("/fxml/main_ui.fxml");
            fxmlLoader.setLocation(location);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);

            setTitle("Transferit v1.0");
            setScene(scene);
            setMainUIController(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MainUIController getMainUIController() {
        return mainUIController;
    }

    public void setMainUIController(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
    }
}
