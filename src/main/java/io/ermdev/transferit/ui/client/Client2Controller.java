package io.ermdev.transferit.ui.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.ermdev.transferit.Endpoint;
import io.ermdev.transferit.TcpClient;
import io.ermdev.transferit.exception.TcpException;
import io.ermdev.transferit.fun.ClientListener;
import io.ermdev.transferit.ui.stage.WelcomeStage;
import io.ermdev.transferit.util.Subscriber;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        for(int i=0; i< 30; i++)
            vboxFile.getChildren().add(new Label("tae"));
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
