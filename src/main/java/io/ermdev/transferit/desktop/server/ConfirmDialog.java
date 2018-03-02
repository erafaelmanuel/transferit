package io.ermdev.transferit.desktop.server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmDialog extends Stage {

    private ConfirmDialogController confirmDialogController;

    public ConfirmDialog(ConfirmDialogListener confirmDialogListener) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/confirm_dialog_ui.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 312, 138);
            initModality(Modality.APPLICATION_MODAL);
            initStyle(StageStyle.UTILITY);
            setScene(scene);
            setTitle("Confirmation");
            setController(loader.getController());
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

    public ConfirmDialogController getController() {
        return confirmDialogController;
    }

    public void setController(ConfirmDialogController confirmDialogController) {
        this.confirmDialogController = confirmDialogController;
    }
}
