package io.ermdev.transferit.desktop.welcome;

import javafx.scene.layout.VBox;

public class OptionMenuBox extends VBox {

    OptionMenuBox() {
        getChildren().add(new OptionItem());
        setWidth(170);
        setHeight(82);
        getStylesheets().add(getClass().getResource("/css/option-menu-style.css").toExternalForm());
    }

}
