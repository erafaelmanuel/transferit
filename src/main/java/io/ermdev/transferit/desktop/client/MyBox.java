package io.ermdev.transferit.desktop.client;

import com.jfoenix.controls.JFXProgressBar;
import io.ermdev.transferit.desktop.util.ImageUtil;
import io.ermdev.transferit.desktop.util.TrafficUtil;
import io.ermdev.transferit.integration.Book;
import io.ermdev.transferit.integration.Item;
import io.ermdev.transferit.integration.ItemSubscriber;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MyBox extends HBox implements ItemSubscriber {

    private final HBox inside = new HBox(2);

    private final VBox imgBox = new VBox();

    private final VBox secondBox = new VBox();

    private final Label lblTitle = new Label();

    private final VBox contentBox = new VBox();

    private final BorderPane progressBox = new BorderPane();

    private final JFXProgressBar progressBar = new JFXProgressBar();

    private final ImageView imgv = new ImageView();

    private final Label lblDetail = new Label();

    private final Label lblDownloaded = new Label();

    private final Label lblPercent = new Label();

    private TrafficUtil trafficUtil = new TrafficUtil();

    private String css = getClass().getResource("/css/jfx-progress-bar.css").toExternalForm();

    private String css2 = getClass().getResource("/css/item-box-style.css").toExternalForm();

    private Item item;

    public MyBox(Item item) {
        setItem(item);
        generateUI();
    }

    private void setItem(Item item) {
        this.item = item;
        trafficUtil.size((long) item.getSize());
        item.subscribe(this);
    }

    private void generateUI() {
        setPadding(new Insets(1, 0, 2, 10));
        getStylesheets().add(css2);
        setId("parent");
        getChildren().add(inside);

        inside.setAlignment(Pos.CENTER_LEFT);
        inside.setPrefHeight(40);
        inside.setPrefWidth(222);
        inside.setPadding(new Insets(3));
        inside.setMinHeight(40);
        inside.setMinWidth(222);
        inside.setId("inside");

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

        progressBar.setPadding(new Insets(0, 0, 0, 5));
        progressBar.setProgress(item.getProgress());
        progressBar.getStylesheets().add(css);
        progressBar.setMaxWidth(175);

        imgv.setFitHeight(24);
        imgv.setFitWidth(24);
        imgv.setImage(new Image(getClass().getResource(ImageUtil.thumbnail(item.getName())).toString()));
        imgv.setId("thumbnail");

        lblPercent.setPadding(new Insets(0, 0, 0, 0));
        lblPercent.setText("0%");
        lblPercent.setFont(Font.font("Calibri", 10));
        lblPercent.setAlignment(Pos.CENTER_RIGHT);
        lblPercent.setId("percent");

        lblDownloaded.setText("0.0 of " + trafficUtil.getLastSize());
        lblDownloaded.setPadding(new Insets(0, 0, 0, 5));
        lblDownloaded.setFont(Font.font("Calibri", 10));
        lblDownloaded.setId("detail");

        lblDetail.setText("Waiting -- " + trafficUtil.getLastSize());
        lblDetail.setPadding(new Insets(0, 0, 2, 5));
        lblDetail.setFont(Font.font("Calibri", 11));
        lblDetail.setId("detail");

        progressBox.setMaxWidth(175);

        contentBox.getChildren().add(lblDetail);

        progressBox.setLeft(lblDownloaded);
        progressBox.setRight(lblPercent);

        secondBox.getChildren().add(lblTitle);
        secondBox.getChildren().add(contentBox);

        imgBox.getChildren().add(imgv);

        inside.getChildren().add(imgBox);
        inside.getChildren().add(secondBox);
    }

    @Override
    public void release(Book<?> book) {
        final double content = (Double) book.getContent();
        final double percent = (100 / item.getSize()) * content;
        Platform.runLater(() -> {
            lblDownloaded.setText(trafficUtil.simply((long) content) + " of " + trafficUtil.getLastSize());
            lblPercent.setText(((int) percent) + "%");
            progressBar.setProgress(percent / 100.0);
        });
    }

    @Override
    public void beforeItemRelease() {
        Platform.runLater(() -> {
            contentBox.getChildren().clear();
            contentBox.getChildren().add(progressBox);
            contentBox.getChildren().add(progressBar);
        });
    }

    @Override
    public void afterItemRelease() {
        Platform.runLater(() -> {
            lblDownloaded.setText(trafficUtil.simply((long) item.getSize()) + " of " + trafficUtil.getLastSize());
            lblPercent.setText("100%");
            progressBar.setProgress(1);
        });
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            lblDetail.setText("Completed -- " + trafficUtil.getLastSize());
            lblDetail.setStyle("-fx-text-fill: #009432");
            contentBox.getChildren().clear();
            contentBox.getChildren().add(lblDetail);
        });
    }
}
