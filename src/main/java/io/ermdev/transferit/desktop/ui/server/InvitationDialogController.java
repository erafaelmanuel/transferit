package io.ermdev.transferit.desktop.ui.server;

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

public class InvitationDialogController implements Initializable {

    private InvitationDialogListener idl;

    @FXML ImageView imgv;

    @FXML Label label;

    public void setListener(InvitationDialogListener idl) {
        this.idl = idl;
    }

    @FXML void onAccept(ActionEvent event) {
        final Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
        if (idl != null) {
            idl.onChoose(true);
        }
    }

    @FXML void onCancel(ActionEvent event) {
        final Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
        if (idl != null) {
            idl.onChoose(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgv.setImage(new Image(getClass().getResource("/image/system/cards.png").toExternalForm()));
    }

    public void setLabelText(String text) {
        if (label != null) {
            label.setText(text);
        }
    }
}
