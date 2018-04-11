package io.ermdev.transferit.desktop.ui.option;

import io.ermdev.transferit.desktop.util.MasterConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    final private MasterConfig masterConfig = new MasterConfig();

    @FXML
    TextField txDir;

    @FXML
    TextField txPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txDir.setText(new File(masterConfig.getDirOrDefault()).getAbsolutePath());
        txPort.setText(String.valueOf(masterConfig.getPortOrDefault()));
    }

    @FXML
    void onBrowse(ActionEvent event) {
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Save to");
        File dir = chooser.showDialog(stage);
        if (dir != null) {
            txDir.setText(dir.getAbsolutePath());
        }
    }

    @FXML
    void onSave(ActionEvent event) {
        if (!txDir.getText().isEmpty()) {
            masterConfig.saveDir(txDir.getText());
        }
        if (!txPort.getText().isEmpty() && txPort.getText().matches("^[0-9]+$")) {
            masterConfig.savePort(Integer.parseInt(txPort.getText()));
        }
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void onCancel(ActionEvent event) {
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
