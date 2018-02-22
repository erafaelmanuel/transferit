package io.ermdev.transferit.desktop.client;

import com.jfoenix.controls.JFXProgressBar;
import io.ermdev.transferit.desktop.util.ImageUtil;
import io.ermdev.transferit.desktop.util.TraficUtil;
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

    private final Label lblDetail = new Label();

    private final Label lblPercent = new Label();

    private Item item;


    public MyBox(Item item) {
        setItem(item);
        generateUI();
    }

    private void setItem(Item item) {
        this.item = item;
        item.subscribe(this);
    }

    private void generateUI() {
        String css = getClass().getResource("/css/jfx-progress-bar.css").toExternalForm();
        String css2 = getClass().getResource("/css/item-box-style.css").toExternalForm();

        setPadding(new Insets(1, 5, 2, 5));
        setId("parent");
        getStylesheets().add(css2);

        inside.setAlignment(Pos.CENTER_LEFT);
        inside.setId("inside");

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
        lblTitle.setId("title");

        progressBar.setPadding(new Insets(6, 0, 0, 5));
        progressBar.setProgress(item.getProgress());
        progressBar.getStylesheets().add(css);
        progressBar.setMaxWidth(156);

        imgv.setFitHeight(24);
        imgv.setFitWidth(24);
        imgv.setImage(new Image(getClass().getResource(ImageUtil.thumbnail(item.getName())).toString()));

        lblPercent.setText("0%");
        lblPercent.setFont(Font.font("Calibri", 10));
        lblPercent.setId("percent");

        lblDetail.setText("Queque -- " + TraficUtil.size(item.getFile().length()));
        lblDetail.setPadding(new Insets(0, 0, 0, 5));
        lblDetail.setFont(Font.font("Calibri", 11));
        lblDetail.setId("detail");

        contentBox.getChildren().add(lblDetail);

        secondBox.getChildren().add(lblTitle);
        secondBox.getChildren().add(contentBox);

        imgBox.getChildren().add(imgv);

        inside.getChildren().add(imgBox);
        inside.getChildren().add(secondBox);

        getChildren().add(inside);
    }

    private boolean fr = true;

//    @Override
//    public void before() {
//        Platform.runLater(() -> {
//            contentBox.getChildren().clear();
//            contentBox.getChildren().add(progressBar);
//            contentBox.getChildren().add(lblPercent);
//        });
//    }
//
//    @Override
//    public void after() {
//        lblDetail.setText("Completed -- " + TraficUtil.size(item.getFile().length()));
//        contentBox.getChildren().clear();
//        contentBox.getChildren().add(lblDetail);
//    }

    @Override
    public void release(Book<?> book) {
        if (fr) {
            Platform.runLater(() -> {
                contentBox.getChildren().clear();
                contentBox.getChildren().add(progressBar);
                contentBox.getChildren().add(lblPercent);
            });
            fr = false;
        }
        if ((Double) book.getContent() < item.getSize()) {
            Platform.runLater(() -> {
                final double percent = (100 / item.getSize()) * (Double) book.getContent();
                progressBar.setProgress(percent / 100.0);
                lblPercent.setText(((int) percent) + "%");
            });
        } else {
            Platform.runLater(() -> {
                final double percent = (100 / item.getSize()) * (Double) book.getContent();
                progressBar.setProgress(percent / 100.0);
                lblPercent.setText("100%");
            });

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                lblDetail.setText("Completed -- " + TraficUtil.size(item.getFile().length()));
                contentBox.getChildren().clear();
                contentBox.getChildren().add(lblDetail);
            });
        }
    }
}
