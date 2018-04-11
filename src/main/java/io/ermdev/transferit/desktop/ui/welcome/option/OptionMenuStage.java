package io.ermdev.transferit.desktop.ui.welcome.option;

import io.ermdev.transferit.desktop.component.BaseStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import java.net.URL;

public class OptionMenuStage extends BaseStage {

    private volatile boolean okDisplay;

    public OptionMenuStage() {
        try {
            final URL fxml = classLoader.getResource("fxml/option-menu.fxml");
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml);

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
