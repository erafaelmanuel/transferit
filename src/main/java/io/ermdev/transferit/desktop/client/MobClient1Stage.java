package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.welcome.WelcomeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MobClient1Stage extends Stage {

    private MobClient1Controller clientUIController;
    private WelcomeListener welcomeListener;

    public MobClient1Stage(WelcomeListener welcomeListener) {
        this.welcomeListener = welcomeListener;
        createClientStage();
    }

    private void createClientStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = getClass().getResource("/fxml/mob_client_ui.fxml");
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

    public MobClient1Controller getController() {
        return clientUIController;
    }

    public void setController(MobClient1Controller clientUIController) {
        this.clientUIController = clientUIController;
    }

    public WelcomeListener getWelcomeListener() {
        return welcomeListener;
    }
}
