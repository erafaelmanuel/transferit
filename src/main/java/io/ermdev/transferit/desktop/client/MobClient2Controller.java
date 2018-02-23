package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.util.ItemManager;
import io.ermdev.transferit.desktop.util.TrafficUtil;
import io.ermdev.transferit.integration.ClientListener;
import io.ermdev.transferit.integration.Item;
import io.ermdev.transferit.integration.TcpClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MobClient2Controller implements ClientListener {

    @FXML VBox container;

    @FXML Label lblStatus;

    @FXML StackPane browser;

    private TcpClient client;

    private List<Item> items = new ArrayList<>();

    private ItemManager itemManager = new ItemManager();

    public void initialize() {
        onClear();
    }

    public void setClient(TcpClient client) {
        this.client = client;
        client.setListener(this);
    }

    @FXML void onClear() {
        items.clear();
        container.getChildren().clear();
        container.getChildren().add(browser);

        lblStatus.setStyle("-fx-background-color: #ff9f43");
        lblStatus.setText("No file to send");
    }

    @FXML void onBrowse() {
        FileChooser fileChooser = new FileChooser();
        List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        if (newFiles != null && newFiles.size() > 0) {
            container.getChildren().clear();
            long size = 0;
            for (File file : newFiles) {
                Item item = new Item(file);
                size += file.length();
                container.getChildren().add(new MyBox(item));
                items.add(item);
            }
            lblStatus.setStyle("-fx-background-color: #ff9f43");
            lblStatus.setText(items.size() + " file(s)   --  Total : " + TrafficUtil.size(size));
        }
    }

    @FXML void onSend() {
        Thread thread = new Thread(() -> {
            if (client != null) {
                itemManager.setItems(items);
                Platform.runLater(() -> {
                    lblStatus.setStyle("-fx-background-color: #fdcb6e");
                    lblStatus.setText("Sending");
                });
                for (Item item : items) {
                    client.sendFile(item.getFile());
                }
                items.clear();
                Platform.runLater(() -> {
                    lblStatus.setStyle("-fx-background-color: #00b894");
                    lblStatus.setText("Completed");
                });
            }
        });
        thread.start();
    }

    @Override
    public void onStart() {
        itemManager.next();
    }

    @Override
    public void onUpdate(double n) {
        if (itemManager.get() != null) {
            itemManager.get().setProgress(n);
        }
    }

    @Override
    public void onComplete(double total) {
        if (itemManager.get() != null) {
            itemManager.get().setProgress(total);
        }
    }
}
