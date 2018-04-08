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
            final String FXML = "/fxml/sender-dashboard.fxml";
            final String CSS = "/css/sender-dashboard-style.css";
            final FXMLLoader loader = new FXMLLoader();
            final URL location = getClass().getResource(FXML);
            final String style = getClass().getResource(CSS).toExternalForm();
            loader.setLocation(location);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 247, 400);
            scene.getStylesheets().add(style);

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

    public void display(double x, double y) {
        setX(x);
        setY(y);
        display();
    }

    public SenderDashboardController getController() {
        return sdc;
    }

    public void setController(SenderDashboardController sdc) {
        this.sdc = sdc;
    }
}
