package io.ermdev.transferit.desktop.ui.server;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;

public class MobServerStage extends BaseStage {

    public MobServerStage() {
        createServerStage();
    }

    private void createServerStage() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader();
            final URL fxml = classLoader.getResource("fxml/mob_server_ui.fxml");
            fxmlLoader.setLocation(fxml);

            final Parent root = fxmlLoader.load();
            final Scene scene = new Scene(root, 247, 400);

            setScene(scene);
            setOnCloseRequest(e -> System.exit(0));
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
