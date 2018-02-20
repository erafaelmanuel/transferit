package io.ermdev.transferit.ui.client;

import io.ermdev.transferit.Endpoint;
import io.ermdev.transferit.TcpClient;
import io.ermdev.transferit.fun.ClientListener;
import io.ermdev.transferit.ui.stage.WelcomeStage;
import io.ermdev.transferit.util.Subscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Client2Controller implements Initializable, Subscriber, ClientListener {

    private WelcomeStage welcomeStage;

    private TcpClient client;

    private Endpoint endpoint;

    @FXML
    private VBox vboxFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i=0; i< 6; i++) {
            VBox vBox = new VBox();
            HBox hBox = new HBox();

            HBox inside = new HBox();
            inside.setAlignment(Pos.CENTER_LEFT);
            inside.setStyle("-fx-border-color:#000");
            inside.setPrefHeight(50);
            inside.setPrefWidth(222);

            inside.setMinHeight(50);
            inside.setMinWidth(222);

            inside.getChildren().add(new Label("tae"));

            hBox.setPadding(new Insets(5, 5, 5, 5));
            hBox.getChildren().add(inside);
            vboxFile.getChildren().add(hBox);
        }
    }

    @Override
    public void update(boolean status) {

    }

    @Override
    public void onTransfer(double count) {

    }

    public void setWelcomeStage(WelcomeStage welcomeStage) {
        this.welcomeStage = welcomeStage;
    }
}
