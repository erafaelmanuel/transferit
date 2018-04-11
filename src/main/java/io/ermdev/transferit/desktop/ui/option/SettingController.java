package io.ermdev.transferit.desktop.ui.option;

import com.jfoenix.controls.JFXButton;
import io.ermdev.transferit.desktop.util.MasterConfig;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    final private MasterConfig masterConfig = new MasterConfig();

    @FXML
    TextField txDir;

    @FXML
    TextField txPort;

    @FXML
    TextField txIP;

    @FXML
    JFXButton btnBrowse;

    private volatile boolean isFocus = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txDir.setText(new File(masterConfig.getDirOrDefault()).getAbsolutePath());
        txPort.setText(String.valueOf(masterConfig.getPortOrDefault()));
        try {
            txIP.setText(Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
        }
        btnBrowse.setVisible(false);
        txDir.focusedProperty().addListener((a, b, c) -> {
            if (c) {
                isFocus = true;
                btnBrowse.setVisible(true);
            } else {
                isFocus = false;
                Thread thread = new Thread(()-> {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isFocus) {
                        btnBrowse.setVisible(false);
                    }
                });
                thread.start();
            }
        });
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
        if (!txPort.getText().isEmpty() && txPort.getText().matches("^[0-9]{1,5}$")) {
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
