package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.Endpoint;
import io.ermdev.transferit.TcpClient;
import io.ermdev.transferit.Receiver;
import io.ermdev.transferit.Transaction;
import io.ermdev.transferit.exception.TransferitException;
import io.ermdev.transferit.fun.ClientListener;
import io.ermdev.transferit.util.Subscriber;
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

public class ClientController implements Subscriber, Initializable, ClientListener {

    private TcpClient client;

    private List<File> files = new ArrayList<>();

    private List<Transaction> transactions = new ArrayList<>();

    private Endpoint endpoint = new Endpoint();

    private int cn;

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
    public void update(boolean status) {
        Thread thread = new Thread(() ->
                Platform.runLater(() -> {
                    if (status) {
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

    @Override
    public void onTransfer(double count) {
        Platform.runLater(() -> {
            transactions.get(cn - 1).setTransfer(count);
            setTableData(tblfiles);
        });
    }

    private void setTableColumn(TableView<Transaction> tableView) {
        TableColumn<Transaction, String> c1 = new TableColumn<>("#");
        c1.setCellValueFactory(new PropertyValueFactory<>("number"));
        c1.setPrefWidth(50);
        c1.setMaxWidth(50);
        c1.setSortable(false);

        TableColumn<Transaction, String> c2 = new TableColumn<>("File Name");
        c2.setCellValueFactory(new PropertyValueFactory<>("filename"));
        c2.setPrefWidth(450);
        c2.setMaxWidth(450);
        c2.setSortable(false);

        TableColumn<Transaction, Double> c3 = new TableColumn<>("Transfer");
        c3.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        c3.setPrefWidth(80);
        c3.setMaxWidth(80);
        c3.setSortable(false);

        tableView.getColumns().add(c1);
        tableView.getColumns().add(c2);
        tableView.getColumns().add(c3);
    }

    private void setTableData(TableView<Transaction> tableView) {
        tableView.getItems().clear();
        for (Transaction transaction : transactions) {
            tableView.getItems().add(transaction);
        }
    }

    @FXML
    public void onActionConnect() {
        Thread thread = new Thread(() -> {
            try {
                Platform.runLater(()-> {
                    btnCDC.setText("Waiting...");
                    btnCDC.setDisable(true);
                });
                if (btnCDC.getText().equals("Connect")) {
                    endpoint.subscribe(this);
                    endpoint.setHost(txHost.getText());
                    endpoint.setPort(23411);

                    client = new TcpClient(endpoint);
                    client.setClientListener(this);
                    client.connect();
                } else {
                    client.disconnect();
                }
            } catch (TransferitException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                client.disconnect();
            }
        });
        thread.start();
    }

    @FXML
    public void onActionClear() {
        files.clear();
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
                boolean isExists = files.parallelStream()
                        .anyMatch(f -> f.getName().equals(file.getName()));
                if (!isExists) {
                    files.add(file);
                }
            }
            transactions.clear();
            for (int ctr = 1; ctr <= files.size(); ctr++) {
                transactions.add(new Transaction(ctr, files.get(ctr - 1).getName(), 0));
            }
            setTableData(tblfiles);
        }
    }

    @FXML
    public void onActionOpenFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File folder = directoryChooser.showDialog(null);
        File subFiles[] = folder.listFiles();
        if (subFiles != null) {
            files.clear();
            for (File file : subFiles) {
                if (file.isFile()) {
                    files.add(file);
                }
            }
            transactions.clear();
            for (int ctr = 1; ctr <= files.size(); ctr++) {
                transactions.add(new Transaction(ctr, files.get(ctr - 1).getName(), 0));
            }
            setTableData(tblfiles);
        }
    }

    @FXML
    public void onActionSend() {
        Thread thread = new Thread(() -> {
            if (client != null && files.size() > 0) {
                for (int ctr = 1; ctr <= files.size(); ctr++) {
                    try {
                        cn = ctr;
                        client.openTransaction(files.get(ctr - 1));
                        transactions.get(ctr - 1).setPercentage("Completed");
                        setTableData(tblfiles);
                    } catch (Exception e) {
                        e.printStackTrace();
                        files.clear();
                        break;
                    }
                }
                files.clear();
            }
        });
        thread.start();
    }
}
