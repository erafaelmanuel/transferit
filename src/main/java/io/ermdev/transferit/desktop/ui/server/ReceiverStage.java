package io.ermdev.transferit.desktop.ui.server;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;

public class ReceiverStage extends BaseStage {

    public ReceiverStage() {
        try {
            final URL fxml = classLoader.getResource("fxml/mob_server_ui.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(fxml);

            final Parent root = fxmlLoader.load();
            final Scene scene = new Scene(root, 247, 400);
            setScene(scene);
            setOnCloseRequest(e -> System.exit(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
