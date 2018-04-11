package io.ermdev.transferit.desktop.component;

import javafx.stage.Stage;

import java.util.Properties;

public class BaseStage extends Stage {

    final protected ClassLoader classLoader = getClass().getClassLoader();
    final protected Properties properties = new Properties();

    public BaseStage() {
        try {

            properties.load(classLoader.getResourceAsStream("config/application.properties"));
            setTitle(properties.getProperty("app.title"));
        } catch (Exception e) {
            setTitle("Transferit");
        }
    }

    public void display(double x, double y) {
        if (x > 0 || y > 0) {
            setX(x);
            setY(y);
        }
        setResizable(false);
        sizeToScene();
        show();
    }
}
