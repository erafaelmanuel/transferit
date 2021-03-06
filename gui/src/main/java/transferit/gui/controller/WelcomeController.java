package transferit.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import transferit.gui.view.WelcomeView;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable, WelcomeView {

//    final private OptionMenuStage optionMenu = new OptionMenuStage();
//
//    final private Cover covers[] = new Cover[5];
//
//    final private WelcomePresenter presenter;
//
//    final private MasterConfig masterConfig = new MasterConfig();

    @FXML
    ImageView option;

    @FXML
    AnchorPane container;

    public WelcomeController() {
//        final WelcomeInteract interact = new WelcomeInteractImpl();
//        presenter = new WelcomePresenterImpl(this, interact);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL style = classLoader.getResource("css/option-menu-style.css");
        final URL image = classLoader.getResource("image/system/more.png");
//        Cover cover;
//
//        covers[3] = new Cover1();
//        covers[1] = new Cover2();
//        covers[2] = new Cover3();
//        covers[0] = new Cover4();
//        covers[4] = new Cover5();
//
//        try {
//            cover = covers[masterConfig.getCoverOrDefault() - 1];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            cover = covers[masterConfig.getCoverOrRandom() - 1];
//        }
//        container.getChildren().add(0, cover);
//        if (image != null) {
//            option.setImage(new Image(image.toString()));
//        }
//        if (style != null) {
//            optionMenu.getScene().getStylesheets().add(style.toExternalForm());
//        }
    }

    @FXML
    void onSend(ActionEvent event) {
//        presenter.clickSend((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    void onReceive(ActionEvent event) {
//        presenter.clickReceive((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    void onOption(MouseEvent event) {
//        presenter.clickOption(event.getScreenX(), event.getScreenY());
    }

    @Override
    public void navigateSend(Stage stage) {
//        final SenderDashboardStage senderDashboardStage = new SenderDashboardStage();
//        senderDashboardStage.display(stage.getX(), stage.getY());
//        stage.hide();
    }

    @Override
    public void navigateReceive(Stage stage) {
//        final ReceiverStage serverStage = new ReceiverStage();
//        serverStage.display(stage.getX(), stage.getY());
//        stage.hide();
    }

    @Override
    public void onErrorSend() {
//        MessageDialogStage messageDialogStage = new MessageDialogStage();
//        messageDialogStage.setMessage("Error!");
//        messageDialogStage.display();
    }

    @Override
    public void onErrorReceive() {
//        MessageDialogStage messageDialogStage = new MessageDialogStage();
//        messageDialogStage.setMessage("The server is busy!");
//        messageDialogStage.display();
    }

    @Override
    public void showOptionMenu(double x, double y) {
//        if (!optionMenu.isDisplayed()) {
//            optionMenu.setX(x);
//            optionMenu.setY(y);
//            optionMenu.display();
//        } else {
//            optionMenu.hide();
//            optionMenu.setDisplay(false);
//        }
    }
}
