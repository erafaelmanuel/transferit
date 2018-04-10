package io.ermdev.transferit.desktop.ui.client;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;

public class SenderBrowserStage extends BaseStage {

    private SenderBrowserController sbc;

    public SenderBrowserStage() {
        createClientStage();
    }

    private void createClientStage() {
        try {
            final URL fxml = classLoader.getResource("fxml/sender-browser.fxml");
            final URL style = classLoader.getResource("css/sender-browser-style.css");
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 247, 400);
            if (style != null) {
                scene.getStylesheets().add(style.toExternalForm());
            }
            //initModality(Modality.APPLICATION_MODAL);
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
