package io.ermdev.transferit.desktop.ui.option;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
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

    @FXML void onBrowse(ActionEvent event) {
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Save to");
        File dir = chooser.showDialog(stage);
        if (dir != null) {
            txDir.setText(dir.getAbsolutePath());
        }
    }

    @FXML void onSave(ActionEvent event) {
        if (!txDir.getText().isEmpty()) {
            properties.put("app.dir", txDir.getText());
        }
        if (!txPort.getText().isEmpty()) {
            properties.put("app.port", txPort.getText());
        }
        try {
            URL url = classLoader.getResource("config/application.properties");
            if (url != null) {
                properties.store(new FileOutputStream(url.toURI().getPath()), "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML void onCancel(ActionEvent event) {
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
