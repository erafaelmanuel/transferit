package io.ermdev.transferit.desktop.ui.dialog;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.net.URL;

public class MessageDialogStage extends BaseStage {

    private MessageDialogController controller;

    public MessageDialogStage() {
        try {
            final URL fxml = classLoader.getResource("fxml/message_dialog.fxml");
            final URL style = classLoader.getResource("css/message-dialog-style.css");
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
            setTitle("Error");
            controller = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        setResizable(false);
        sizeToScene();
        show();
    }

    public void setMessage(String message) {
        if (controller != null) {
            controller.setLabelText(message);
        }
    }
}
