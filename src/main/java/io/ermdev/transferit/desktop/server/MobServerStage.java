package io.ermdev.transferit.desktop.server;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;

public class MobServerStage extends BaseStage {

    private MobServerController serverController;

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

    public void display(double x, double y) {
        setX(x);
        setY(y);
        display();
    }

    public MobServerController getController() {
        return serverController;
    }

    public void setController(MobServerController serverController) {
        this.serverController = serverController;
    }
}
