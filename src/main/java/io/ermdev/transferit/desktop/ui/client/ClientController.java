package io.ermdev.transferit.desktop.ui.client;

import io.ermdev.transferit.integration.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Deprecated
public class ClientController implements Subscriber, Initializable, ClientListener {

    private Client client;

    private List<Transaction> transactions = new ArrayList<>();

    private Endpoint endpoint = new Endpoint();

    @FXML
    private TextField txHost;

    @FXML
    private Button btnCDC;

    @FXML
    private Button btnSend;

    @FXML
    private TableView<Transaction> tblfiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSend.setDisable(true);
        tblfiles.getColumns().clear();
        setTableColumn(tblfiles);
    }

    @Override
    public void release(Book<?> book) {
        Thread thread = new Thread(() ->
                Platform.runLater(() -> {
                    if (book.getContent() instanceof Boolean && (Boolean) book.getContent()) {
                        btnCDC.setText("Disconnect");
                        btnSend.setDisable(false);
                    } else {
                        btnCDC.setText("Connect");
                        btnSend.setDisable(true);
                    }
                    btnCDC.setDisable(false);
                })
        );
        thread.start();
    }

    private void setTableColumn(TableView<Transaction> tableView) {
        TableColumn<Transaction, Integer> c1 = new TableColumn<>("#");
        c1.setCellValueFactory(new PropertyValueFactory<>("index"));
        c1.setPrefWidth(50);
        c1.setMaxWidth(50);
        c1.setSortable(false);

        TableColumn<Transaction, String> c2 = new TableColumn<>("File Name");
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        c2.setPrefWidth(370);
        c2.setMaxWidth(370);
        c2.setSortable(false);

        TableColumn<Transaction, String> c4 = new TableColumn<>("Size");
        c4.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        c4.setPrefWidth(80);
        c4.setMaxWidth(80);
        c4.setSortable(false);

        TableColumn<Transaction, Double> c3 = new TableColumn<>("Transfer");
        c3.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        c3.setPrefWidth(80);
        c3.setMaxWidth(80);
        c3.setSortable(false);

        tableView.getColumns().add(c1);
        tableView.getColumns().add(c2);
        tableView.getColumns().add(c4);
        tableView.getColumns().add(c3);
    }

    private void setTableData(TableView<Transaction> tableView) {
        Platform.runLater(() -> {
            tableView.getItems().clear();
            for (int ctr = 0; ctr < transactions.size(); ctr++) {
                transactions.get(ctr).setIndex(ctr + 1);
                tableView.getItems().add(transactions.get(ctr));
            }
        });
    }

    @FXML
    public void onActionConnect() {
        Thread thread = new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    btnCDC.setText("Waiting...");
                    btnCDC.setDisable(true);
                });
                if (btnCDC.getText().equals("Connect")) {
                    endpoint.subscribe(this);
                    endpoint.setHost(txHost.getText());
                    endpoint.setPort(23411);
                    client = new LinkClient(endpoint);
                    client.connect();
                } else {
                    client.disconnect();
                }
            } catch (ClientException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                client.disconnect();
            }
        });
        thread.start();
    }

    @FXML
    public void onActionClear() {
        transactions.clear();
        setTableData(tblfiles);
    }

    @FXML
    public void onActionClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void onActionBrowse() {
        FileChooser fileChooser = new FileChooser();
        List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        if (newFiles != null && newFiles.size() > 0) {
            for (File file : newFiles) {
                boolean isExists = transactions.parallelStream()
                        .anyMatch(f -> f.getName().equals(file.getName()));
                if (!isExists) {
                    transactions.add(new Transaction(file));
                }
            }
            setTableData(tblfiles);
        }
    }

    @FXML
    public void onActionOpenFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File folder = directoryChooser.showDialog(null);
        if (folder != null) {
            File subFiles[] = folder.listFiles();
            if (subFiles != null) {
                transactions.clear();
                for (File file : subFiles) {
                    if (file.isFile()) {
                        transactions.add(new Transaction(file));
                    }
                }
                setTableData(tblfiles);
            }
        }
    }

    @FXML
    public void onActionSend() {
        Thread thread = new Thread(() -> {
            if (client != null && transactions.size() > 0) {
                ItemClient itemClient = new ItemClient(client, this);
                for (Transaction item : transactions) {
                    itemClient.sendItem(item);
                }
            }
        });
        thread.start();
    }

    @Override
    public void onSendFileStart(Item item) {
        item.setProgress(item.getProgress());
        setTableData(tblfiles);
    }

    @Override
    public void onSendFileUpdate(Item item, double n) {
        item.setProgress(n);
        setTableData(tblfiles);
    }

    @Override
    public void onSendFileComplete(Item item) {
        item.setProgress(item.getSize());
        setTableData(tblfiles);
    }

    @Override
    public void onTransferSpeed(String speed) {

    }
}
