package io.ermdev.transferit.desktop.ui.welcome;

import io.ermdev.transferit.desktop.component.Cover;
import io.ermdev.transferit.desktop.cover.*;
import io.ermdev.transferit.desktop.ui.client.SenderDashboardStage;
import io.ermdev.transferit.desktop.ui.server.MobServerStage;
import io.ermdev.transferit.desktop.ui.welcome.option.OptionMenuStage;
import io.ermdev.transferit.desktop.ui.welcome.v2.WelcomePresenter;
import io.ermdev.transferit.desktop.ui.welcome.v2.WelcomePresenterImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable, WelcomeView {

    final private OptionMenuStage optionMenu = new OptionMenuStage();

    final private Cover covers[] = new Cover[5];

    final private WelcomePresenter presenter;

    private Stage stage;

    @FXML
    ImageView option;

    @FXML
    AnchorPane container;

    public WelcomeController() {
        io.ermdev.transferit.desktop.ui.welcome.v2.WelcomeInteractImpl interact =
                new io.ermdev.transferit.desktop.ui.welcome.v2.WelcomeInteractImpl();
        presenter = new WelcomePresenterImpl(this, interact);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL style = classLoader.getResource("css/option-menu-style.css");
        final URL image = classLoader.getResource("image/system/more.png");
        final int GENERATED_NUMBER = (int) ((Math.random() * 5));

        covers[0] = new Cover1();
        covers[1] = new Cover2();
        covers[2] = new Cover3();
        covers[3] = new Cover4();
        covers[4] = new Cover5();

        container.getChildren().add(0, covers[GENERATED_NUMBER]);
        if (image != null) {
            option.setImage(new Image(image.toString()));
        }
        if (style != null) {
            optionMenu.getScene().getStylesheets().add(style.toExternalForm());
        }
    }

    @FXML
    void onSend() {
        presenter.clickSend();
    }

    @FXML
    void onReceive() {
        presenter.clickReceive();
    }

    @FXML
    void onOption(MouseEvent event) {
        if (!optionMenu.isDisplayed()) {
            optionMenu.setX(event.getScreenX());
            optionMenu.setY(event.getScreenY());
            optionMenu.display();
        } else {
            optionMenu.hide();
            optionMenu.setDisplay(false);
        }
    }

    @Override
    public void navigateSend() {
        if (stage != null) {
            final SenderDashboardStage sds = new SenderDashboardStage();
            sds.display(stage.getX(), stage.getY());
            stage.hide();
        }
    }

    @Override
    public void navigateReceive() {
        if (stage != null) {
            MobServerStage stage = new MobServerStage();
            stage.display(stage.getX(), stage.getY());
            stage.hide();
        }
    }

    @Override
    public void onErrorSend() {
        System.out.println("Send error");
    }

    @Override
    public void onErrorReceive() {
        System.out.println("Receive error");
    }
}
