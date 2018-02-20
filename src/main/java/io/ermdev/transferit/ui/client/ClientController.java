package io.ermdev.transferit.ui.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.ermdev.transferit.Endpoint;
import io.ermdev.transferit.TcpClient;
import io.ermdev.transferit.exception.TcpException;
import io.ermdev.transferit.fun.ClientListener;
import io.ermdev.transferit.ui.stage.WelcomeStage;
import io.ermdev.transferit.util.Subscriber;
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

public class ClientController implements Initializable, Subscriber, ClientListener {

    @FXML
    private ImageView imgv1;

    @FXML
    private ImageView imgvback;

    @FXML
    private JFXButton btnConnect;

    @FXML
    private JFXButton btnSendFile;

    @FXML
    private Label lblStatus;

    @FXML
    private JFXTextField txtHost;

    private WelcomeStage welcomeStage;

    private TcpClient client;

    private Endpoint endpoint;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        endpoint = new Endpoint();
        endpoint.subscribe(this);

        imgv1.setImage(new Image(getClass().getResource("/image/img1_client1.png").toString()));
        imgvback.setImage(new Image(getClass().getResource("/image/img_prev.png").toString()));
    }

    @Override
    public void update(boolean status) {
        Thread thread = new Thread(() -> {
            if (status) {
                Platform.runLater(() -> {
                    lblStatus.setText("You are connected!");
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
                });
            }
        });
        thread.start();
    }

    @Override
    public void onTransfer(double count) {

    }

    public void setWelcomeStage(WelcomeStage welcomeStage) {
        this.welcomeStage = welcomeStage;
    }

    @FXML
    protected void onActionConnect() {
        Thread thread = new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    lblStatus.setVisible(true);
                    lblStatus.setStyle("-fx-text-fill:#000");
                    lblStatus.setText("Connecting . . .");
                    btnConnect.setDisable(true);
                    btnConnect.requestFocus();
                });
                if (btnConnect.getText().equalsIgnoreCase("Connect")) {
                    endpoint.setHost(txtHost.getText());
                    endpoint.setPort(23411);

                    client = new TcpClient(endpoint);
                    client.setClientListener(this);
                    client.connect();
                } else {
                    client.disconnect();
                }
            } catch (TcpException e) {
                Platform.runLater(() -> {
                    lblStatus.setText("Connection failed!");
                    lblStatus.setStyle("-fx-text-fill:#eb4d4b");
                    btnConnect.setDisable(false);
                    btnSendFile.setDisable(true);
                });
            }
        });
        thread.start();
    }

    @FXML
    protected void onMousePressedBack(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.close();

        if(client != null) {
            client.disconnect();
        }

        if (welcomeStage != null) {
            welcomeStage.show();
        }
    }
}
