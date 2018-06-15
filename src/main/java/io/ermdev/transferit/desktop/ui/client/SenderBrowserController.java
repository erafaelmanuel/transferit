package io.ermdev.transferit.desktop.ui.client;

import io.ermdev.transferit.core.client.Client;
import io.ermdev.transferit.core.client.ClientListener;
import io.ermdev.transferit.core.protocol.Item;
import io.ermdev.transferit.core.client.ClientItem;
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

    private ClientItem clientItem;

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

    public void setClient(final Client client) {
        clientItem = new ClientItem(client, this);
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
        final FileChooser fileChooser = new FileChooser();
        final List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        long size = 0;
        if (newFiles != null && newFiles.size() > 0) {
            container.getChildren().clear();
            for (File file : newFiles) {
                final Item item = new Item(file);

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
            clientItem.pause();
            btnSend.setText("Start");
        } else if (btnSend.getText().equalsIgnoreCase("Start")) {
            okSend = true;
            clientItem.play();
            btnSend.setText("Pause");
        } else {
            sender = new Thread(() -> {
                okSend = true;
                if (clientItem != null) {
                    Platform.runLater(() -> {
                        btnClear.setDisable(true);
                        btnSend.setText("Pause");
                    });
                    for (Item item : items) {
                        clientItem.getClient().sendFile(item.getFile());
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (Item item : items) {
                        while (!okSend) {
                        }
                        clientItem.sendItem(item);
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
    void onDrag(final DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        }
    }

    @FXML
    void onDrop(final DragEvent event) {
        final Dragboard db = event.getDragboard();
        long size = 0;
        if (db.hasFiles()) {
            container.getChildren().clear();
            for (File file : db.getFiles()) {
                if (file.isFile()) {
                    final Item item = new Item(file);

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
    public void onSendFileStart(final Item item) {
        item.notifyBefore();
    }

    @Override
    public void onSendFileUpdate(final Item item, final double n) {
        item.setProgress(n);
    }

    @Override
    public void onSendFileComplete(final Item item) {
        item.setProgress(item.getSize());
        item.notifyAfter();
    }

    @Override
    public void onTransferSpeed(final String speed) {
        Platform.runLater(() -> {
            lblStatus.setStyle("-fx-background-color: #0984e3");
            lblStatus.setText("Transfer speed : " + speed);
        });
    }
}
