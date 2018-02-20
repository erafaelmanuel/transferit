package io.ermdev.transferit.ui.client;

import io.ermdev.transferit.integration.Endpoint;
import io.ermdev.transferit.integration.TcpClient;
import io.ermdev.transferit.integration.ClientListener;
import io.ermdev.transferit.ui.stage.WelcomeStage;
import io.ermdev.transferit.integration.Subscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
            VBox secondBox = new VBox();
            secondBox.setPrefHeight(40);
            secondBox.setPrefWidth(167);
            secondBox.setStyle("-fx-border-color:#000");

            VBox firstBox = new VBox();
            firstBox.setAlignment(Pos.CENTER_LEFT);
            firstBox.setPrefHeight(40);
            firstBox.setPrefWidth(40);
            firstBox.setStyle("-fx-border-color:#000");

            firstBox.getChildren().add(new Label("Image"));


            HBox parent = new HBox();

            HBox inside = new HBox(4);
            inside.setAlignment(Pos.CENTER_LEFT);
            inside.setStyle("-fx-border-color:#000");
            inside.setPrefHeight(50);
            inside.setPrefWidth(222);
            inside.setPadding(new Insets(3));

            inside.setMinHeight(50);
            inside.setMinWidth(222);

            inside.getChildren().add(firstBox);
            inside.getChildren().add(secondBox);

            parent.setPadding(new Insets(5));
            parent.getChildren().add(inside);
            vboxFile.getChildren().add(parent);
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
