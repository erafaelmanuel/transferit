package io.ermdev.transferit.desktop.welcome;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class WelcomeStage extends Stage {

    private WelcomeController wc;

    public WelcomeStage() {
        createWelcomeStage();
    }

    private void createWelcomeStage() {
        try {
            final String FXML_URL = "/fxml/mob_welcome_ui.fxml";
            final String CSS_URL = "/css/welcome-ui-style.css";

            final FXMLLoader loader = new FXMLLoader();
            final URL location = this.getClass().getResource(FXML_URL);
            final String style = getClass().getResource(CSS_URL).toExternalForm();
            loader.setLocation(location);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 247, 400);

            scene.getStylesheets().add(style);
            setTitle("Transferit v1.0");
            setScene(scene);
            setController(loader.getController());
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
        return wc;
    }

    public void setController(WelcomeController wc) {
        this.wc = wc;
    }
}
