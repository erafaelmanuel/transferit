package io.ermdev.transferit.desktop.welcome;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OptionMenu extends Stage {

    private volatile boolean okDisplay;

    public OptionMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/option_ui.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 170, 82);

            initStyle(StageStyle.UNDECORATED);

            setResizable(false);
            setScene(scene);
            focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    hide();
                    setDisplay(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        okDisplay = true;
        show();
    }

    public boolean isDisplayed() {
        return okDisplay;
    }

    public void setDisplay(boolean okDisplay) {
        this.okDisplay = okDisplay;
    }
}
