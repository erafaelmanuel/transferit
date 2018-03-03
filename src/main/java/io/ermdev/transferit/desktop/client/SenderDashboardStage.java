package io.ermdev.transferit.desktop.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

final public class SenderDashboardStage extends Stage {

    private SenderDashboardController sdc;

    public SenderDashboardStage() {
        try {
            final String FXML_URL = "/fxml/sender-dashboard.fxml";
            final FXMLLoader loader = new FXMLLoader();
            final URL location = getClass().getResource(FXML_URL);
            loader.setLocation(location);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 247, 400);

            setTitle("Transferit v1.0");
            setScene(scene);
            setController(loader.getController());
            setOnCloseRequest(e -> System.exit(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        setResizable(false);
        sizeToScene();
        show();
    }

    public SenderDashboardController getController() {
        return sdc;
    }

    public void setController(SenderDashboardController sdc) {
        this.sdc = sdc;
    }
}
