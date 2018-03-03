package io.ermdev.transferit.desktop.welcome;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class WelcomeStage extends Stage {

    private WelcomeController welcomeController;
    private WelcomeListener welcomeListener;

    public WelcomeStage(WelcomeListener welcomeListener) {
        this.welcomeListener = welcomeListener;
        createWelcomeStage();
    }

    private void createWelcomeStage() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader();
            final URL location = this.getClass().getResource("/fxml/mob_welcome_ui.fxml");
            final String style = getClass().getResource("/css/welcome-ui-style.css").toExternalForm();

            fxmlLoader.setLocation(location);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 247, 400);
            scene.getStylesheets().add(style);
            setTitle("Transferit v1.0");
            setScene(scene);
            setController(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        setResizable(false);
        sizeToScene();
        show();
    }

    public WelcomeController getController() {
        return welcomeController;
    }

    public void setController(WelcomeController welcomeController) {
        this.welcomeController = welcomeController;
    }

    public WelcomeListener getWelcomeListener() {
        return welcomeListener;
    }
}
