package io.ermdev.transferit.desktop.ui.client;

import io.ermdev.transferit.desktop.ui.welcome.WelcomeInteract;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

@Deprecated
public class ClientStage extends Stage {

    private ClientController clientUIController;
    private WelcomeInteract.WelcomeListener onWelcomeClose;

    public ClientStage(WelcomeInteract.WelcomeListener onWelcomeClose) {
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

    public WelcomeInteract.WelcomeListener getOnWelcomeClose() {
        return onWelcomeClose;
    }
}
