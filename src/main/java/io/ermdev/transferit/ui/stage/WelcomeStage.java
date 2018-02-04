package io.ermdev.transferit.ui.stage;

import io.ermdev.transferit.ui.callback.OnWelcomeClose;
import io.ermdev.transferit.ui.controller.WelcomeUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class WelcomeStage extends Stage {

    private WelcomeUIController welcomeUIController;
    private OnWelcomeClose onWelcomeClose;

    public WelcomeStage(OnWelcomeClose onWelcomeClose) {
        this.onWelcomeClose = onWelcomeClose;
        createWelcomeStage();
    }

    private void createWelcomeStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = this.getClass().getResource("/fxml/welcome_ui.fxml");
            fxmlLoader.setLocation(location);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 337, 235);

            setTitle("Transferit v1.0");
            setScene(scene);
            setWelcomeUIController(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WelcomeUIController getWelcomeUIController() {
        return welcomeUIController;
    }

    public void setWelcomeUIController(WelcomeUIController welcomeUIController) {
        this.welcomeUIController = welcomeUIController;
    }

    public OnWelcomeClose getOnWelcomeClose() {
        return onWelcomeClose;
    }
}
