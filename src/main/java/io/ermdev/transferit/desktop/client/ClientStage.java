package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.welcome.WelcomeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ClientStage extends Stage {

    private ClientController clientUIController;
    private WelcomeListener onWelcomeClose;

    public ClientStage(WelcomeListener onWelcomeClose) {
        this.onWelcomeClose = onWelcomeClose;
        createClientStage();
    }

    private void createClientStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = this.getClass().getResource("/fxml/client_ui.fxml");
            fxmlLoader.setLocation(location);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);

            setTitle("Transferit v1.0");
            setScene(scene);
            setClientUIController(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientController getClientUIController() {
        return clientUIController;
    }

    public void setClientUIController(ClientController clientUIController) {
        this.clientUIController = clientUIController;
    }

    public WelcomeListener getOnWelcomeClose() {
        return onWelcomeClose;
    }
}
