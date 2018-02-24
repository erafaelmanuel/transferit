package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.util.TrafficUtil;
import io.ermdev.transferit.integration.Client;
import io.ermdev.transferit.integration.Item;
import io.ermdev.transferit.integration.ItemClient;
import io.ermdev.transferit.integration.v2.ClientListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MobClient2Controller implements ClientListener {

    @FXML
    VBox container;

    @FXML
    Label lblStatus;

    @FXML
    StackPane browser;

    @FXML
    Button btnSend;

    @FXML
    Button btnClear;

    private ItemClient client;

    private List<Item> items = new ArrayList<>();

    public void initialize() {
        onClear();
    }

    public void setClient(Client c) {
        client = new ItemClient(c, this);
    }

    @FXML
    void onClear() {
        items.clear();
        container.getChildren().clear();
        container.getChildren().add(browser);
        lblStatus.setStyle("-fx-background-color: #ff9f43");
        lblStatus.setText("No file to send");
        btnSend.setDisable(true);
    }

    @FXML
    void onBrowse() {
        FileChooser fileChooser = new FileChooser();
        List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        if (newFiles != null && newFiles.size() > 0) {
            long size = 0;
            container.getChildren().clear();
            for (File file : newFiles) {
                Item item = new Item(file);
                size += file.length();
                container.getChildren().add(new MyBox(item));
                items.add(item);
            }
            lblStatus.setStyle("-fx-background-color: #ff9f43");
            lblStatus.setText(items.size() + " file(s)   --  Total : " + new TrafficUtil().size(size));
            btnSend.setDisable(false);
        }
    }

    @FXML
    void onSend() {
        if (btnSend.getText().equalsIgnoreCase("Cancel")) {
            return;
        }
        Thread thread = new Thread(() -> {
            if (client != null) {
                Platform.runLater(() -> {
                    btnClear.setDisable(true);
                    btnSend.setText("Cancel");
                });
                for (Item item : items) {
                    client.sendItem(item);
                }
                Platform.runLater(() -> {
                    lblStatus.setStyle("-fx-background-color: #00b894");
                    lblStatus.setText("Completed");
                    btnClear.setDisable(false);
                    btnSend.setText("Send");
                });
                items.clear();
            }
        });
        thread.start();
    }

    @FXML
    void onDrag(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    void onDrop(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            long size = 0;
            container.getChildren().clear();
            for (File file : db.getFiles()) {
                Item item = new Item(file);
                size += file.length();
                container.getChildren().add(new MyBox(item));
                items.add(item);
            }
            lblStatus.setStyle("-fx-background-color: #ff9f43");
            lblStatus.setText(items.size() + " file(s)   --  Total : " + new TrafficUtil().size(size));
            btnSend.setDisable(false);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @Override
    public void onSendFileStart(Item item) {

    }

    @Override
    public void onSendFileUpdate(Item item, double n) {
        item.setProgress(n);
    }

    @Override
    public void onSendFileComplete(Item item) {
        item.setProgress(item.getSize());
    }

    @Override
    public void onTransferSpeed(String speed) {
        Platform.runLater(() -> {
            lblStatus.setStyle("-fx-background-color: #0984e3");
            lblStatus.setText("Transfer speed : " + speed);
        });
    }
}
