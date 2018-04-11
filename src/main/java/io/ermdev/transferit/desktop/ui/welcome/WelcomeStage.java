package io.ermdev.transferit.desktop.ui.welcome;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;

public class WelcomeStage extends BaseStage {

    public WelcomeStage() {
        try {
            final URL fxml = classLoader.getResource("fxml/welcome.fxml");
            final URL style = classLoader.getResource("css/welcome-style.css");
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 247, 400);
            if (style != null) {
                scene.getStylesheets().add(style.toExternalForm());
            }
            setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(double x, double y) {
        if (x > 0 || y > 0) {
            setX(x);
            setY(y);
        }
        setResizable(false);
        sizeToScene();
        show();
    }
}
