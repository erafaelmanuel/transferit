package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.welcome.OnWelcomeClose;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MobClientStage extends Stage {

    private MobClientController clientUIController;
    private OnWelcomeClose onWelcomeClose;

    public MobClientStage(OnWelcomeClose onWelcomeClose) {
        this.onWelcomeClose = onWelcomeClose;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MobClientController getController() {
        return clientUIController;
    }

    public void setController(MobClientController clientUIController) {
        this.clientUIController = clientUIController;
    }

    public OnWelcomeClose getOnWelcomeClose() {
        return onWelcomeClose;
    }
}
