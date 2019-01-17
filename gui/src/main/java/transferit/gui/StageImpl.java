package transferit.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class StageImpl extends Stage {

    final protected Properties properties = new Properties();

    private StageImpl () {
        try {
            properties.load(getClass().getResourceAsStream("/config/application.properties"));
            setTitle(properties.getProperty("app.title"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StageImpl(String css, String fxml) {
        this (css, fxml, 247, 400);
    }

    public StageImpl(String css, String fxml, double x, double y) {
        this ();
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            final Parent root = loader.load();
            final Scene scene = new Scene(root, x, y);
            final URL url;

            if ((url =  getClass().getResource(css))!= null) {
                scene.getStylesheets().add(url.toExternalForm());
            }
            setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(double x, double y) {
        if (x > 0 || y > 0) {
            setX(x);
            setY(y);
        }
        setResizable(false);
        sizeToScene();
        show();
    }
}
