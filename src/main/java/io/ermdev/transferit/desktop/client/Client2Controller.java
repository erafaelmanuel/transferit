package io.ermdev.transferit.desktop.client;

import io.ermdev.transferit.desktop.util.ItemManager;
import io.ermdev.transferit.integration.ClientListener;
import io.ermdev.transferit.integration.Item;
import io.ermdev.transferit.integration.TcpClient;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Client2Controller implements ClientListener {

    @FXML VBox icBox;

    private TcpClient client;

    private List<Item> items = new ArrayList<>();

    private ItemManager itemManager = new ItemManager();

    private Item currentItem;

    @FXML
    void onFile() {
        FileChooser fileChooser = new FileChooser();
        List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        if (newFiles != null && newFiles.size() > 0) {
            for (File file : newFiles) {
                Item item = new Item(file);
                icBox.getChildren().add(new MyBox(item));
                items.add(item);
            }
        }
    }

    @FXML
    void onSend() {
        Thread thread = new Thread(() -> {
            if (client != null) {
                itemManager.setItems(items);
                for (Item item : items) {
                    try {
                        client.sendFile(item.getFile());
                    } catch (Exception e) {
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
    public void onStart() {
        currentItem = itemManager.next();
    }

    @Override
    public void onUpdate(double n) {
        if (currentItem != null) {
            currentItem.setProgress(n);
        }
    }

    @Override
    public void onComplete(double total) {
        if (currentItem != null) {
            currentItem.setProgress(total);
        }
    }

    public void setClient(TcpClient client) {
        this.client = client;
        client.setListener(this);
    }
}
