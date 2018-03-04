package io.ermdev.transferit.desktop.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.ermdev.transferit.desktop.component.cover.CoverError;
import io.ermdev.transferit.desktop.component.cover.CoverInfo;
import io.ermdev.transferit.desktop.component.cover.CoverSuccess;
import io.ermdev.transferit.desktop.component.cover.CoverWait;
import io.ermdev.transferit.desktop.welcome.WelcomeInteract;
import io.ermdev.transferit.integration.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SenderDashboardController implements Initializable, Subscriber {

    @FXML
    ImageView imgvback;

    @FXML
    JFXButton btnConnect;

    @FXML
    JFXButton btnSendFile;

    @FXML
    JFXTextField txtHost;

    @FXML
    AnchorPane container;

    private WelcomeInteract wi;

    private Client client;

    private Endpoint endpoint;

    private Thread connector;

    private SenderBrowserStage client2Stage = new SenderBrowserStage();

    public void setWelcomeInteract(WelcomeInteract wi) {
        this.wi = wi;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        container.getChildren().add(0, new CoverInfo());
        endpoint = new Endpoint();
        endpoint.subscribe(this);
        imgvback.setImage(new Image(getClass().getResource("/image/system/img_prev.png").toString()));
    }

    @Override
    public void release(Book<?> book) {
        Thread thread = new Thread(() -> {
            if (book.getContent() instanceof Boolean && (Boolean) book.getContent()) {
                Platform.runLater(() -> {
                    container.getChildren().remove(0);
                    container.getChildren().add(0, new CoverSuccess());
                    btnConnect.setText("Disconnect");
                    btnConnect.setStyle("-fx-background-color: #747d8c; -fx-border-color: #e84393;");
                    btnConnect.setDisable(false);
                    btnSendFile.setDisable(false);
                });
            } else {
                Platform.runLater(() -> {
                    container.getChildren().remove(0);
                    container.getChildren().add(0, new CoverError());
                    btnConnect.setText("Connect");
                    btnConnect.setStyle("-fx-background-color: #747d8c; -fx-border-color: #ffa502;");
                    btnConnect.setDisable(false);
                    btnSendFile.setDisable(true);
                    client2Stage.close();
                });
            }
        });
        thread.start();
    }

    @FXML
    void onConnection() {
        connector = new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    CoverWait coverWait = new CoverWait();
                    coverWait.setLabelText("Connecting");

                    container.getChildren().remove(0);
                    container.getChildren().add(0, coverWait);
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
                    CoverError coverError = new CoverError();
                    coverError.setLabelText(e.getMessage());

                    container.getChildren().remove(0);
                    container.getChildren().add(0, coverError);
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
        if (wi != null) {
            wi.setDisplay();
        }
    }
}
