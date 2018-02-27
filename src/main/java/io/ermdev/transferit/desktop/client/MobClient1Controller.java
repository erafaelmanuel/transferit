package io.ermdev.transferit.desktop.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.ermdev.transferit.desktop.welcome.WelcomeStage;
import io.ermdev.transferit.integration.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MobClient1Controller implements Initializable, Subscriber {

    @FXML
    ImageView imgv1;

    @FXML
    ImageView imgvback;

    @FXML
    JFXButton btnConnect;

    @FXML
    JFXButton btnSendFile;

    @FXML
    Label lblStatus;

    @FXML
    JFXTextField txtHost;

    private WelcomeStage welcomeStage;

    private Client client;

    private Endpoint endpoint;

    private Thread connector;

    private MobClient2Stage client2Stage = new MobClient2Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        endpoint = new Endpoint();
        endpoint.subscribe(this);
        imgv1.setImage(new Image(getClass().getResource("/image/bg-cl/img3.png").toString()));
        imgvback.setImage(new Image(getClass().getResource("/image/system/img_prev.png").toString()));
    }

    @Override
    public void release(Book<?> book) {
        Thread thread = new Thread(() -> {
            if (book.getContent() instanceof Boolean && (Boolean) book.getContent()) {
                Platform.runLater(() -> {
                    lblStatus.setText("Connection Successful");
                    lblStatus.setStyle("-fx-text-fill:#6ab04c");
                    btnConnect.setText("Disconnect");
                    btnConnect.setStyle("-fx-background-color:#ff7675");
                    btnConnect.setDisable(false);
                    btnSendFile.setDisable(false);
                });
            } else {
                Platform.runLater(() -> {
                    lblStatus.setText("You are disconnected");
                    lblStatus.setStyle("-fx-text-fill:#000");
                    btnConnect.setText("Connect");
                    btnConnect.setStyle("-fx-background-color:#00b894");
                    btnConnect.setDisable(false);
                    btnSendFile.setDisable(true);
                    client2Stage.close();
                });
            }
        });
        thread.start();
    }

    public void setWelcomeStage(WelcomeStage welcomeStage) {
        this.welcomeStage = welcomeStage;
    }

    @FXML
    void onConnection() {
        connector = new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    lblStatus.setVisible(true);
                    lblStatus.setStyle("-fx-text-fill:#000");
                    lblStatus.setText("Connecting . . .");
                    btnConnect.setDisable(true);
                });
                if (btnConnect.getText().equalsIgnoreCase("Connect")) {
                    endpoint.setHost(txtHost.getText());
                    endpoint.setPort(23411);
                    client = new LinkClient(endpoint);
                    client.connect();
                } else {
                    client.disconnect();
                }
            } catch (ClientException e) {
                Platform.runLater(() -> {
                    lblStatus.setText("Connection Failed");
                    lblStatus.setStyle("-fx-text-fill:#eb4d4b");
                    btnConnect.setDisable(false);
                    btnSendFile.setDisable(true);
                });
            }
            connector = null;
        });
        connector.start();
    }

    @FXML
    void onFile() {
        client2Stage.getController().setClient(client);
        client2Stage.getController().initialize();
        client2Stage.display();
    }

    @FXML
    void onBack(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.close();

        if (client != null) {
            client.disconnect();
        }
        if (welcomeStage != null) {
            welcomeStage.display();
        }
    }
}
