package io.ermdev.transferit.ui.stage;

import io.ermdev.transferit.ui.welcome.OnWelcomeClose;
import io.ermdev.transferit.ui.controller.ServerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ServerStage extends Stage {

    private ServerController serverController;
    private OnWelcomeClose onWelcomeClose;

    public ServerStage(OnWelcomeClose onWelcomeClose) {
        this.onWelcomeClose = onWelcomeClose;
        createServerStage();
    }

    private void createServerStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = this.getClass().getResource("/fxml/server_ui.fxml");
            fxmlLoader.setLocation(location);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);

            setTitle("Transferit v1.0");
            setScene(scene);
            setServerController(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerController getServerController() {
        return serverController;
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    public OnWelcomeClose getOnWelcomeClose() {
        return onWelcomeClose;
    }


}
