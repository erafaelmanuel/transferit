package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.stage.WelcomeStage;
import io.ermdev.transferit.integration.ClientListener;
import io.ermdev.transferit.integration.Endpoint;
import io.ermdev.transferit.integration.Subscriber;
import io.ermdev.transferit.integration.TcpClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        for (int i = 0; i < 12; i++) {
            vboxFile.getChildren().add(new MyBox());
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
