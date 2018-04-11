package io.ermdev.transferit.desktop.ui.option;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    final private ClassLoader classLoader = getClass().getClassLoader();
    final private Properties properties = new Properties();

    @FXML
    TextField txDir;

    @FXML
    TextField txPort;

    public SettingController() {
        try {
            properties.load(classLoader.getResourceAsStream("config/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String dir = properties.getProperty("app.dir", "");
        if (!dir.isEmpty()) {
            txDir.setText(new File(dir).getAbsolutePath());
        }
        txPort.setText(properties.getProperty("app.port", "0"));
    }

    @FXML void onSave() {

    }

    @FXML void onCancel() {

    }
}
