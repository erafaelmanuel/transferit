package io.ermdev.transferit.desktop.ui.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageDialogController implements Initializable {

    @FXML
    ImageView imgv;

    @FXML
    Label label;

    @FXML
    void onClose(ActionEvent event) {
        final Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgv.setImage(new Image(getClass().getResource("/image/system/warning.png").toExternalForm()));
    }

    public void setLabelText(String text) {
        if (label != null) {
            label.setText(text);
        }
    }
}
