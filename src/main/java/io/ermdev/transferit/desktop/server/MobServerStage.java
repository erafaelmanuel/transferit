package io.ermdev.transferit.desktop.server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MobServerStage extends Stage {

    private MobServerController serverController;

    public MobServerStage() {
        createServerStage();
    }

    private void createServerStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = this.getClass().getResource("/fxml/mob_server_ui.fxml");
            fxmlLoader.setLocation(location);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 247, 400);

            setTitle("Transferit v1.0");
            setScene(scene);
            setController(fxmlLoader.getController());
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

    public MobServerController getController() {
        return serverController;
    }

    public void setController(MobServerController serverController) {
        this.serverController = serverController;
    }
}
