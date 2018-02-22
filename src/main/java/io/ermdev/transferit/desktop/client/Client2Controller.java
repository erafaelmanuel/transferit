package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.stage.WelcomeStage;
import io.ermdev.transferit.integration.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Client2Controller implements Initializable, Subscriber, ClientListener {

    private WelcomeStage welcomeStage;

    private TcpClient client;

    private Endpoint endpoint;

    @FXML
    private VBox vboxFile;

    @FXML
    void onFile() {
        FileChooser fileChooser = new FileChooser();
        List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        if (newFiles != null && newFiles.size() > 0) {
            for (File file : newFiles) {
                vboxFile.getChildren().add(new MyBox(new Item(file)));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 12; i++) {

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
