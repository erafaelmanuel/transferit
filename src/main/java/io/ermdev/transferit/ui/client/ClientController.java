package io.ermdev.transferit.ui.client;

import io.ermdev.transferit.ui.stage.WelcomeStage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML private ImageView imgv1;

    @FXML private ImageView imgvback;

    private WelcomeStage welcomeStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgv1.setImage(new Image(getClass().getResource("/image/img1_client1.png").toString()));
        imgvback.setImage(new Image(getClass().getResource("/image/img_prev.png").toString()));
    }

    @FXML
    protected void onMousePressedBack(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.close();

        if(welcomeStage != null) {
            welcomeStage.show();
        }
    }

    public void setWelcomeStage(WelcomeStage welcomeStage) {
        this.welcomeStage = welcomeStage;
    }
}
