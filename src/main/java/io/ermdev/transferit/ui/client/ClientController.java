package io.ermdev.transferit.ui.client;

import com.jfoenix.controls.JFXButton;
import io.ermdev.transferit.ui.stage.WelcomeStage;
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

public class ClientController implements Initializable {

    @FXML private ImageView imgv1;

    @FXML private ImageView imgvback;

    @FXML private JFXButton btnConnect;

    @FXML private JFXButton btnSendFile;

    @FXML private Label lblStatus;

    private WelcomeStage welcomeStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgv1.setImage(new Image(getClass().getResource("/image/img1_client1.png").toString()));
        imgvback.setImage(new Image(getClass().getResource("/image/img_prev.png").toString()));
    }

    public void setWelcomeStage(WelcomeStage welcomeStage) {
        this.welcomeStage = welcomeStage;
    }

    @FXML
    protected void onMousePressedBack(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.close();

        if (welcomeStage != null) {
            welcomeStage.show();
        }
    }

    private boolean s = true;

    @FXML
    protected void onActionConnect() {
        if(s) {
            lblStatus.setVisible(true);
            btnConnect.setText("Disconnect");
            btnConnect.setStyle("-fx-background-color:#ff7675");
            btnSendFile.setDisable(false);
            s = false;
        } else {
            lblStatus.setText("Waiting ...");
            lblStatus.setStyle("-fx-text-fill:#000000");
            btnConnect.setText("Connect");
            btnConnect.setStyle("-fx-background-color:#00b894");
            btnSendFile.setDisable(true);
            s = true;
        }
    }
}
