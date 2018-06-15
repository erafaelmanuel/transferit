package io.ermdev.transferit.desktop.component;

import com.jfoenix.controls.JFXProgressBar;
import io.ermdev.transferit.core.arch.Book;
import io.ermdev.transferit.core.arch.ItemSubscriber;
import io.ermdev.transferit.core.protocol.Item;
import io.ermdev.transferit.desktop.util.ImageUtil;
import io.ermdev.transferit.desktop.util.TrafficUtil;
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

import java.net.URL;

public class ItemBox extends HBox implements ItemSubscriber {

    private final HBox firstBox = new HBox(2);

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

    private URL progressBarStyle = getClass().getResource("/css/jfx-progress-bar.css");

    private URL itemBoxStyle = getClass().getResource("/css/item-box-style.css");

    private Item item;

    public ItemBox(Item item) {
        setItem(item);
        generateUI();
    }

    private void setItem(Item item) {
        this.item = item;
        trafficUtil.size((long) item.getSize());
        item.subscribe(this);
    }

    private void createFirstBox() {
        firstBox.setAlignment(Pos.CENTER_LEFT);
        firstBox.setPrefHeight(40);
        firstBox.setPrefWidth(222);
        firstBox.setPadding(new Insets(3));
        firstBox.setMinHeight(40);
        firstBox.setMinWidth(222);
        firstBox.setId("inside");
        firstBox.getChildren().add(imgBox);
        firstBox.getChildren().add(secondBox);

        imgv.setFitHeight(24);
        imgv.setFitWidth(24);
        imgv.setImage(new Image(getClass().getResource(ImageUtil.thumbnail(item.getName())).toString()));
        imgv.setId("thumbnail");

        imgBox.setAlignment(Pos.CENTER);
        imgBox.setPrefHeight(24);
        imgBox.setPrefWidth(24);
        imgBox.getChildren().add(imgv);
    }

    private void createSecondBox() {
        secondBox.setAlignment(Pos.CENTER_LEFT);
        secondBox.setPrefHeight(40);
        secondBox.setPrefWidth(186);
        secondBox.getChildren().add(lblTitle);
        secondBox.getChildren().add(contentBox);

        lblTitle.setText(item.getName());
        lblTitle.setFont(Font.font("Calibri", 10));
        lblTitle.setPadding(new Insets(0, 0, 0, 5));
        lblTitle.setId("title");

        progressBar.setPadding(new Insets(0, 0, 0, 5));
        progressBar.setProgress(item.getProgress());
        progressBar.getStylesheets().add(progressBarStyle.toExternalForm());
        progressBar.setMaxWidth(175);


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
        progressBox.setLeft(lblDownloaded);
        progressBox.setRight(lblPercent);

        contentBox.getChildren().add(lblDetail);
    }


    private void generateUI() {
        setPadding(new Insets(1, 0, 2, 10));
        getStylesheets().add(itemBoxStyle.toExternalForm());
        setId("parent");
        getChildren().add(firstBox);

        createFirstBox();
        createSecondBox();
    }

    @Override
    public void onRelease(Book<?> book) {
        final double content = (Double) book.getContent();
        final double percent = (100 / item.getSize()) * content;
        Platform.runLater(() -> {
            lblDownloaded.setText(trafficUtil.round((long) content) + " of " + trafficUtil.getLastSize());
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
            lblDownloaded.setText(trafficUtil.round((long) item.getSize()) + " of " + trafficUtil.getLastSize());
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
