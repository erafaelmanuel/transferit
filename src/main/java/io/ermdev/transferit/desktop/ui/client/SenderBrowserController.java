package io.ermdev.transferit.desktop.ui.client;

import io.ermdev.transferit.core.client.Client;
import io.ermdev.transferit.core.client.ClientListener;
import io.ermdev.transferit.core.Item;
import io.ermdev.transferit.core.client.ItemClient;
import io.ermdev.transferit.desktop.component.ItemBox;
import io.ermdev.transferit.desktop.util.TrafficUtil;
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

public class SenderBrowserController implements ClientListener {

    final private List<Item> items = new ArrayList<>();

    private ItemClient client;

    private Thread sender;

    private volatile boolean okSend;

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

    public void initialize() {
        onClear();
        btnSend.setText("Send");
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
        long size = 0;
        if (newFiles != null && newFiles.size() > 0) {
            container.getChildren().clear();
            for (File file : newFiles) {
                Item item = new Item(file);
                size += file.length();
                container.getChildren().add(new ItemBox(item));
                items.add(item);
            }
            lblStatus.setStyle("-fx-background-color: #ff9f43");
            lblStatus.setText(items.size() + " file(s)   --  Total : " + new TrafficUtil().size(size));
            btnSend.setDisable(false);
        }
    }

    @FXML
    void onSend() {
        if (btnSend.getText().equalsIgnoreCase("Pause")) {
            okSend = false;
            client.pause();
            btnSend.setText("Start");
        } else if (btnSend.getText().equalsIgnoreCase("Start")) {
            okSend = true;
            client.play();
            btnSend.setText("Pause");
        } else {
            sender = new Thread(() -> {
                okSend = true;
                if (client != null) {
                    Platform.runLater(() -> {
                        btnClear.setDisable(true);
                        btnSend.setText("Pause");
                    });
                    for (Item item : items) {
                        client.getClient().sendFile(item.getFile());
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (Item item : items) {
                        while (!okSend) {
                        }
                        client.sendItem(item);
                    }
                    Platform.runLater(() -> {
                        lblStatus.setStyle("-fx-background-color: #00b894");
                        lblStatus.setText("Completed");
                        btnClear.setDisable(false);
                        btnSend.setText("Send");
                    });
                    items.clear();
                    sender = null;
                }
            });
            sender.start();
        }
    }

    @FXML
    void onDrag(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        }
    }

    @FXML
    void onDrop(DragEvent event) {
        Dragboard db = event.getDragboard();
        long size = 0;
        if (db.hasFiles()) {
            container.getChildren().clear();
            for (File file : db.getFiles()) {
                if (file.isFile()) {
                    Item item = new Item(file);
                    size += file.length();
                    container.getChildren().add(new ItemBox(item));
                    items.add(item);
                }
            }
            if (items.size() < 1) {
                container.getChildren().add(browser);
            } else {
                lblStatus.setStyle("-fx-background-color: #ff9f43");
                lblStatus.setText(items.size() + " file(s)   --  Total : " + new TrafficUtil().size(size));
                btnSend.setDisable(false);
            }
            event.setDropCompleted(true);
            event.consume();
        }
    }

    @Override
    public void onSendFileStart(Item item) {
        item.notifyBefore();
    }

    @Override
    public void onSendFileUpdate(Item item, double n) {
        item.setProgress(n);
    }

    @Override
    public void onSendFileComplete(Item item) {
        item.setProgress(item.getSize());
        item.notifyAfter();
    }

    @Override
    public void onTransferSpeed(String speed) {
        Platform.runLater(() -> {
            lblStatus.setStyle("-fx-background-color: #0984e3");
            lblStatus.setText("Transfer speed : " + speed);
        });
    }
}
