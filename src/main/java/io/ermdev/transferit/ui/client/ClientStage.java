package io.ermdev.transferit.ui.client;

import io.ermdev.transferit.fun.OnWelcomeClose;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ClientStage extends Stage {

    private ClientController clientUIController;
    private OnWelcomeClose onWelcomeClose;

    public ClientStage(OnWelcomeClose onWelcomeClose) {
        this.onWelcomeClose = onWelcomeClose;
        createClientStage();
    }

    private void createClientStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = getClass().getResource("/fxml/client_flatui.fxml");
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

    public ClientController getClientController() {
        return clientUIController;
    }

    public void setClientController(ClientController clientUIController) {
        this.clientUIController = clientUIController;
    }

    public OnWelcomeClose getOnWelcomeClose() {
        return onWelcomeClose;
    }
}
