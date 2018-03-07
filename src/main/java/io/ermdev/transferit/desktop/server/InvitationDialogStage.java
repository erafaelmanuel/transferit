package io.ermdev.transferit.desktop.server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InvitationDialogStage extends Stage {

    private InvitationDialogController idc;

    InvitationDialogStage(InvitationDialogListener confirmDialogListener) {
        try {
            final String FXML = "/fxml/invitation_dialog.fxml";
            final String CSS = "/css/invitation-dialog-style.css";

            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML));

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 312, 138);
            scene.getStylesheets().add(getClass().getResource(CSS).toExternalForm());

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
