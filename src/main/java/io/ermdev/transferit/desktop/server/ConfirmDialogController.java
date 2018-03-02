package io.ermdev.transferit.desktop.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmDialogController implements Initializable {

    private ConfirmDialogListener confirmDialogListener;

    @FXML
    ImageView imgv;

    public void setListener(ConfirmDialogListener confirmDialogListener) {
        this.confirmDialogListener = confirmDialogListener;
    }

    @FXML
    void onAccept(ActionEvent event) {
        final Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
        if (confirmDialogListener != null) {
            confirmDialogListener.onChoose(true);
        }
    }

    @FXML
    void onCancel(ActionEvent event) {
        final Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
        if (confirmDialogListener != null) {
            confirmDialogListener.onChoose(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgv.setImage(new Image(getClass().getResource("/image/system/cards.png").toExternalForm()));
    }
}
