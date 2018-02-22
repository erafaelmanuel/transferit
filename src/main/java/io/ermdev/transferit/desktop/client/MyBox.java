package io.ermdev.transferit.desktop.client;

import com.jfoenix.controls.JFXProgressBar;
import io.ermdev.transferit.integration.Book;
import io.ermdev.transferit.integration.Item;
import io.ermdev.transferit.integration.Subscriber;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MyBox extends HBox implements Subscriber {

    private final HBox inside = new HBox(2);
    private final VBox imgBox = new VBox();
    private final VBox secondBox = new VBox();
    private final Label lblTitle = new Label();
    private final HBox contentBox = new HBox(5);
    private final JFXProgressBar progressBar = new JFXProgressBar();
    private final ImageView imgv = new ImageView();
    private Item item;

    Label label = new Label();

    public MyBox(Item item) {
        this.item = item;
        item.subscribe(this);
        createUI();
    }

    private void createUI() {
        String css = getClass().getResource("/css/jfx-progress-bar.css").toExternalForm();
        setStyle("-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
        setPadding(new Insets(1, 5, 2, 5));

        inside.setAlignment(Pos.CENTER_LEFT);
        inside.setStyle("-fx-background-radius: 3, 3 , 3, 3");
        inside.setStyle("-fx-background-color: #636e72");
        inside.setPrefHeight(40);
        inside.setPrefWidth(222);
        inside.setPadding(new Insets(3));
        inside.setMinHeight(40);
        inside.setMinWidth(222);

        imgBox.setAlignment(Pos.CENTER);
        imgBox.setPrefHeight(24);
        imgBox.setPrefWidth(24);

        secondBox.setAlignment(Pos.CENTER_LEFT);
        secondBox.setPrefHeight(40);
        secondBox.setPrefWidth(186);

        lblTitle.setText(item.getName());
        lblTitle.setFont(Font.font("Calibri", 10));
        lblTitle.setPadding(new Insets(0, 0, 0, 5));
        lblTitle.setStyle("-fx-text-fill: #fff");

        progressBar.setPadding(new Insets(6, 0, 0, 5));
        progressBar.setProgress(item.getProgress());
        progressBar.getStylesheets().add(css);
        progressBar.setMaxWidth(156);

        imgv.setFitHeight(24);
        imgv.setFitWidth(24);
        imgv.setImage(new Image(getClass().getResource("/image/img_mp3.png").toString()));

        label.setText("0 %");
        label.setStyle("-fx-text-fill: #fff");
        label.setFont(Font.font("Calibri", 10));

        contentBox.getChildren().add(progressBar);
        contentBox.getChildren().add(label);

        secondBox.getChildren().add(lblTitle);
        secondBox.getChildren().add(contentBox);

        imgBox.getChildren().add(imgv);

        inside.getChildren().add(imgBox);
        inside.getChildren().add(secondBox);

        getChildren().add(inside);
    }

    @Override
    public void release(Book<?> book) {
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                final double percent = (100 / item.getSize()) * (Double) book.getContent();
                progressBar.setProgress(percent / 100.0);
                label.setText(((int) percent) + " %");
            });
        });
        thread.start();
    }
}
