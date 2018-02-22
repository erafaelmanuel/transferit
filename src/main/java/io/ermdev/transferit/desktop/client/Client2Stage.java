package io.ermdev.transferit.desktop.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class Client2Stage extends Stage {

    private Client2Controller clientUIController;

    public Client2Stage() {
        createClientStage();
    }

    private void createClientStage() {
        try {
            final URL location = getClass().getResource("/fxml/mob_client2_ui.fxml");
            final String title = "Transferit v1.0";

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 247, 400);

            initModality(Modality.APPLICATION_MODAL);
            setTitle(title);
            setResizable(false);
            setScene(scene);
            setController(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client2Controller getController() {
        return clientUIController;
    }

    public void setController(Client2Controller clientUIController) {
        this.clientUIController = clientUIController;
    }
}
