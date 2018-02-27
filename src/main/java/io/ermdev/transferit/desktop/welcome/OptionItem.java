package io.ermdev.transferit.desktop.welcome;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class OptionItem extends HBox {

    ImageView imageView = new ImageView();

    Label label = new Label();

    OptionItem() {
        imageView.setImage(new Image(getClass().getResource("/image/system/settings.png").toExternalForm()));
        label.setText("Settings");
        setSpacing(10);
        setPadding(new Insets(2, 0, 2, 5));
        setId("settings");
    }
}
