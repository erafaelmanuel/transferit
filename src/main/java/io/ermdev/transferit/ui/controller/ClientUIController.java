package io.ermdev.transferit.ui.controller;

import io.ermdev.transferit.local.client.BasicClient;
import io.ermdev.transferit.local.client.Receiver;
import io.ermdev.transferit.local.client.Transaction;
import io.ermdev.transferit.util.Subscriber;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientUIController implements Subscriber, Initializable {

    private BasicClient client;
    private List<File> files = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private Receiver receiver = new Receiver();

    @FXML
    private TextField txHost;
    @FXML
    private Button btnCDC;
    @FXML
    private Button btnSend;
    @FXML
    private TableView<Transaction> tblfiles;

    @FXML
    public void onActionConnect() {
        try {
            if (btnCDC.getText().equals("Connect")) {
                receiver.subscribe(this);
                receiver.setHost(txHost.getText());
                receiver.setPort(23411);
                receiver.setConnected(true);
                client = new BasicClient(receiver);
            } else {
                receiver.setConnected(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to connect.");
        }
    }

    @FXML
    public void onActionSend() {
        Thread thread = new Thread(() -> {
            if (client != null && files.size() > 0) {
                for (File file : files) {
                    try {
                        client.openTransaction(file);
                        System.out.println(file.getName());
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

    @FXML
    public void onActionBrowse() {
        FileChooser fileChooser = new FileChooser();
        List<File> newFiles = fileChooser.showOpenMultipleDialog(null);
        if (newFiles != null && newFiles.size() > 0) {
            files.addAll(newFiles);
            transactions.clear();
            for(int ctr=1; ctr<= files.size(); ctr++) {
                transactions.add(new Transaction(ctr, files.get(ctr-1).getName(), 0));
            }
            setTableData(tblfiles);
        }
    }

    @Override
    public void update(boolean status) {
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                if (status) {
                    btnCDC.setText("Disconnect");
                    btnSend.setDisable(false);
                } else {
                    btnCDC.setText("Connect");
                    btnSend.setDisable(true);
                }
            });
        });
        thread.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSend.setDisable(true);
        tblfiles.getColumns().clear();
        setTableColumn(tblfiles);
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

        TableColumn<Transaction, Integer> c3 = new TableColumn<>("Transfer");
        c3.setCellValueFactory(new PropertyValueFactory<>("transfer"));
        c3.setPrefWidth(80);
        c3.setMaxWidth(80);
        c3.setSortable(false);

        tableView.getColumns().add(c1);
        tableView.getColumns().add(c2);
        tableView.getColumns().add(c3);
    }


    private void setTableData(TableView<Transaction> tableView) {
        tableView.getItems().clear();
        for(Transaction transaction : transactions) {
            tableView.getItems().add(transaction);
        }
    }
//
//        TableColumn<Object, String> uPass = new TableColumn<>("Password");
//        uPass.setCellValueFactory(new PropertyValueFactory<>("password"));
//        uPass.setPrefWidth(200);
//        uPass.setSortable(false);
//
//        TableColumn<Object, String> uType = new TableColumn<>("Type");
//        uType.setCellValueFactory(new PropertyValueFactory<>("userType"));
//        uType.setPrefWidth(200);
//        uType.setSortable(false);
//
//        TableColumn<Object, String> uIA = new TableColumn<>("Activated");
//        uIA.setCellValueFactory(new PropertyValueFactory<>("activated"));
//        uIA.setPrefWidth(200);
//        uIA.setSortable(false);
//
//        TableColumn<Object, String> uDate = new TableColumn<>("Registration Date");
//        uDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
//        uDate.setPrefWidth(200);
//        uDate.setSortable(false);
//
//        tblData.getColumns().add(uId);
//        tblData.getColumns().add(uUser);
//        tblData.getColumns().add(uPass);
//        tblData.getColumns().add(uType);
//        tblData.getColumns().add(uIA);
//        tblData.getColumns().add(uDate);
//
//        USER_LIST.clear();
//        for (UserDetail userDetail : userDetailDao.getUserDetailList()) {
//            userDetail.setActivated(userDetail.isActivated() ? "YES":"NO");
//            tblData.getItems().add(userDetail);
//            USER_LIST.add(userDetail);
//        }

}
