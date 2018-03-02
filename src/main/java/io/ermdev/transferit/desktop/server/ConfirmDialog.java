package io.ermdev.transferit.desktop.server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmDialog extends Stage {

    private ConfirmDialogListener confirmDialogListener;

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
            loader.getController().setListener(confirmDialogListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        setResizable(false);
        sizeToScene();
        show();
    }

}
