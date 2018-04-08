package io.ermdev.transferit.desktop.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Cover extends VBox {

    private final Label label = new Label();

    private final ImageView imageView = new ImageView();

    public Cover() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPrefSize(247, 174);
        setMaxSize(247, 174);

        imageView.setFitHeight(85);
        imageView.setFitWidth(85);
        label.setFont(Font.font("Calibri", 11));

        getChildren().add(imageView);
        getChildren().add(label);
    }

    public void setBackgroundColor(String color) {
        setStyle(String.format("-fx-background-color: %s", color));
    }

    public void setLabelColor(String color) {
        label.setStyle(String.format("-fx-text-fill: %s", color));
    }

    public void setLabelText(String text) {
        label.setText(text);
    }

    public void setImage(String url) {
        imageView.setImage(new Image(getClass().getResource(url).toExternalForm()));
    }
}
