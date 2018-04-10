package io.ermdev.transferit.desktop.ui.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.ermdev.transferit.integration.*;
import io.ermdev.transferit.desktop.cover.CoverError;
import io.ermdev.transferit.desktop.cover.CoverInfo;
import io.ermdev.transferit.desktop.cover.CoverSuccess;
import io.ermdev.transferit.desktop.cover.CoverWait;
import io.ermdev.transferit.desktop.ui.welcome.WelcomeInteract;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

    private WelcomeInteract wi;

    private Client client;

    private Endpoint endpoint;

    private Thread connector;

    private SenderBrowserStage sbs = new SenderBrowserStage();

    @FXML ImageView imgvback;

    @FXML JFXButton btnConnect;

    @FXML JFXButton btnSendFile;

    @FXML JFXTextField txtHost;

    @FXML AnchorPane container;

    public void setWelcomeInteract(WelcomeInteract wi) {
        this.wi = wi;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        container.getChildren().add(0, new CoverInfo());
        imgvback.setImage(new Image(getClass().getResource("/image/system/img_prev.png").toString()));

        sbs = new SenderBrowserStage();
        endpoint = new Endpoint();
        endpoint.subscribe(this);
    }

    @Override
    public void release(Book<?> book) {
        Thread thread = new Thread(() -> {
            if (book.getContent() instanceof Boolean && (Boolean) book.getContent()) {
                Platform.runLater(() -> {
                    container.getChildren().remove(0);
                    container.getChildren().add(0, new CoverSuccess());
                    btnConnect.setText("Disconnect");
                    btnConnect.setId("btnDisconnect");
                    btnConnect.setDisable(false);
                    btnSendFile.setDisable(false);
                    txtHost.setDisable(true);
                    if (txtHost.getText().isEmpty()) {
                        txtHost.setText("127.0.0.1");
                    }
                });
            } else {
                Platform.runLater(() -> {
                    container.getChildren().remove(0);
                    container.getChildren().add(0, new CoverError());
                    btnConnect.setText("Connect");
                    btnConnect.setId("btnConnect");
                    btnConnect.setDisable(false);
                    btnSendFile.setDisable(true);
                    txtHost.setDisable(false);
                    sbs.close();
                });
            }
        });
        thread.start();
    }

    @FXML void onConnection() {
        connector = new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    final CoverWait coverWait = new CoverWait();
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
                    final CoverError coverError = new CoverError();
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

    @FXML void onFile(ActionEvent event) {
        final Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        sbs.getController().setClient(client);
        sbs.getController().initialize();
        sbs.display(stage.getX() + 247 + 10, stage.getY());
    }

    @FXML void onBack(MouseEvent event) {
        final Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.close();
        if (client != null) {
            client.disconnect();
        }
        if (wi != null) {
            wi.setDisplay(stage.getX(), stage.getY());
        }
    }
}
