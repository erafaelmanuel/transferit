package io.ermdev.transferit.desktop.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class SenderBrowserStage extends Stage {

    private SenderBrowserController sbc;

    public SenderBrowserStage() {
        createClientStage();
    }

    private void createClientStage() {
        try {
            final String FXML = "/fxml/sender-browser.fxml";
            final String CSS = "/css/sender-browser-style.css";
            final URL location = getClass().getResource(FXML);

            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 247, 400);
            scene.getStylesheets().add(CSS);

            //initModality(Modality.APPLICATION_MODAL);
            setTitle("Transferit v1.0");
            setScene(scene);
            sbc = loader.getController();
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

    public SenderBrowserController getController() {
        return sbc;
    }

}
