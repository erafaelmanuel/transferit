package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.welcome.OnWelcomeClose;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Client2Stage extends Stage {

    private Client2Controller clientUIController;
    private OnWelcomeClose onWelcomeClose;

    public Client2Stage(OnWelcomeClose onWelcomeClose) {
        this.onWelcomeClose = onWelcomeClose;
        createClientStage();
    }

    private void createClientStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = getClass().getResource("/fxml/mob_client2_ui.fxml");
            fxmlLoader.setLocation(location);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 247, 400);

            setTitle("Transferit v1.0");
            setScene(scene);
            setClientController(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client2Controller getClientController() {
        return clientUIController;
    }

    public void setClientController(Client2Controller clientUIController) {
        this.clientUIController = clientUIController;
    }

    public OnWelcomeClose getOnWelcomeClose() {
        return onWelcomeClose;
    }
}
