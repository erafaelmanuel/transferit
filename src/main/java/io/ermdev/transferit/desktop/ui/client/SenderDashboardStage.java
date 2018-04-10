package io.ermdev.transferit.desktop.ui.client;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;

final public class SenderDashboardStage extends BaseStage {

    private SenderDashboardController sdc;

    public SenderDashboardStage() {
        try {
            final URL fxml = classLoader.getResource("fxml/sender-dashboard.fxml");
            final URL style = classLoader.getResource("css/sender-dashboard-style.css");
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 247, 400);
            if (style != null) {
                scene.getStylesheets().add(style.toExternalForm());
            }

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
