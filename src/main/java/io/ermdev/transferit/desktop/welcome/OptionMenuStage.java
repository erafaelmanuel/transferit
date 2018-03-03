package io.ermdev.transferit.desktop.welcome;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OptionMenuStage extends Stage {

    private volatile boolean okDisplay;

    OptionMenuStage() {
        try {
            final String FXML = "/fxml/option-menu.fxml";
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML));

            final Parent root = loader.load();
            final Scene scene = new Scene(root, 170, 81);

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
