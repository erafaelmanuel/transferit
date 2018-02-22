package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.stage.WelcomeStage;
import io.ermdev.transferit.integration.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Client2Controller implements Initializable, Subscriber, ClientListener {

    private WelcomeStage welcomeStage;

    private TcpClient client;

    List<Item> items = new ArrayList<>();

    List<File> files = new ArrayList<>();

    @FXML
    private VBox vboxFile;

    @FXML
    void onFile() {
        FileChooser fileChooser = new FileChooser();
        List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        if (newFiles != null && newFiles.size() > 0) {
            for (File file : newFiles) {
                Item item = new Item(file);
                MyBox myBox = new MyBox(item);

                vboxFile.getChildren().add(myBox);
                items.add(item);
                files.add(file);
            }
        }
        items.get(1).setProgress(454345);
    }

    int cn = 0;

    @FXML
    void onSend() {
        System.out.println(items.size());
        Thread thread = new Thread(() -> {
            if (client != null && items.size() > 0) {
                for (int ctr = 1; ctr <= items.size(); ctr++) {
                    System.out.println("tae");
                    try {
                        cn = ctr;
                        client.sendFile(files.get(ctr - 1));
                        items.get(cn - 1).setProgress(files.get(cn - 1).length());
                    } catch (Exception e) {
                        e.printStackTrace();
                        items.clear();
                        break;
                    }
                }
                items.clear();
            }
        });
        thread.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 12; i++) {

        }
    }

    @Override
    public void release(Book<?> book) {

    }

    @Override
    public void onTransfer(double count) {
        items.get(cn - 1).setProgress(count);
    }

    public void setWelcomeStage(WelcomeStage welcomeStage) {
        this.welcomeStage = welcomeStage;
    }

    public void setClient(TcpClient client) {
        this.client = client;
        client.setListener(this);
    }
}
