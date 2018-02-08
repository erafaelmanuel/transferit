package io.ermdev.transferit.ui.stage;

import io.ermdev.transferit.ui.callback.OnWelcomeClose;
import io.ermdev.transferit.ui.controller.ClientUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ClientStage extends Stage {

    private ClientUIController clientUIController;
    private OnWelcomeClose onWelcomeClose;

    public ClientStage(OnWelcomeClose onWelcomeClose) {
        this.onWelcomeClose = onWelcomeClose;
        createClientStage();
    }

    private void createClientStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = this.getClass().getResource("/fxml/client_ui.fxml");
            fxmlLoader.setLocation(location);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 337, 235);

            setTitle("Transferit v1.0");
            setScene(scene);
            setClientUIController(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientUIController getClientUIController() {
        return clientUIController;
    }

    public void setClientUIController(ClientUIController clientUIController) {
        this.clientUIController = clientUIController;
    }

    public OnWelcomeClose getOnWelcomeClose() {
        return onWelcomeClose;
    }
}