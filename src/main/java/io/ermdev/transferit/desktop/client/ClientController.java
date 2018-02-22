package io.ermdev.transferit.desktop.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.ermdev.transferit.integration.*;
import io.ermdev.transferit.desktop.stage.WelcomeStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField txtHidden;

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
                    txtHidden.requestFocus();
                });
                if (btnConnect.getText().equalsIgnoreCase("Connect")) {
                    endpoint.setHost(txtHost.getText());
                    endpoint.setPort(23411);

                    client = new TcpClient(endpoint);
                    client.setListener(this);
                    client.connect();
                } else {
                    client.disconnect();
                }
            } catch (TcpException e) {
                Platform.runLater(() -> {
                    lblStatus.setText("Connection Failed");
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
