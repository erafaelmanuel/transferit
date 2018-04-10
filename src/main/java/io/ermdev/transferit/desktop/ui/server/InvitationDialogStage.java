package io.ermdev.transferit.desktop.ui.server;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.net.URL;

public class InvitationDialogStage extends BaseStage {

    private InvitationDialogController idc;

    InvitationDialogStage(InvitationDialogListener confirmDialogListener) {
        try {
            final URL fxml = classLoader.getResource("fxml/invitation_dialog.fxml");
            final URL style = classLoader.getResource("css/invitation-dialog-style.css");

            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml);

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 312, 138);
            if (style != null) {
                scene.getStylesheets().add(style.toExternalForm());
            }

            initModality(Modality.APPLICATION_MODAL);
            initStyle(StageStyle.UTILITY);
            setScene(scene);
            setTitle("Sender Invitation");

            idc = loader.getController();
            getController().setListener(confirmDialogListener);
            setOnCloseRequest(event -> {
                if (confirmDialogListener != null) confirmDialogListener.onChoose(false);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        setResizable(false);
        sizeToScene();
        show();
    }

    public InvitationDialogController getController() {
        return idc;
    }
}
