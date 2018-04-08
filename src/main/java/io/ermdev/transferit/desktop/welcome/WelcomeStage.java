package io.ermdev.transferit.desktop.welcome;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class WelcomeStage extends Stage {

    private WelcomeController wc;

    public WelcomeStage() {
        try {
            final String FXML = "/fxml/welcome.fxml";
            final String CSS = "/css/welcome-style.css";

            final FXMLLoader loader = new FXMLLoader();
            final URL location = this.getClass().getResource(FXML);
            final String style = getClass().getResource(CSS).toExternalForm();
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

    public void display(double x, double y) {
        setX(x);
        setY(y);
        display();
    }

    public WelcomeController getController() {
        return wc;
    }

    public void setController(WelcomeController wc) {
        this.wc = wc;
    }
}
