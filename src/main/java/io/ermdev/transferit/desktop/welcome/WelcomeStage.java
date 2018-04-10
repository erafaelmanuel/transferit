package io.ermdev.transferit.desktop.welcome;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;

public class WelcomeStage extends BaseStage {

    private WelcomeController wc;

    public WelcomeStage() {
        try {
            final String FXML = "fxml/welcome.fxml";
            final String CSS = "css/welcome-style.css";

            final FXMLLoader loader = new FXMLLoader();
            final URL location = classLoader.getResource(FXML);
            final URL style = classLoader.getResource(CSS);
            loader.setLocation(location);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 247, 400);

            if (style != null) {
                scene.getStylesheets().add(style.toExternalForm());
            }
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
