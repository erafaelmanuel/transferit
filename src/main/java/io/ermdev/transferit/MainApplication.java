package io.ermdev.transferit;

import io.ermdev.transferit.desktop.client.SenderDashboardStage;
import io.ermdev.transferit.desktop.server.MobServerStage;
import io.ermdev.transferit.desktop.welcome.WelcomeInteract;
import io.ermdev.transferit.desktop.welcome.WelcomeInteractImpl;
import io.ermdev.transferit.desktop.welcome.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application implements WelcomeInteract.WelcomeListener {

    private WelcomeStage ws;
    private WelcomeInteract wi;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ws = new WelcomeStage();
        ws.getController().setWelcomeListener(this);

        wi = new WelcomeInteractImpl(this);
        wi.setDisplay();
    }

    @Override
    public void onShow() {
        ws.display();
    }

    @Override
    public void onSelectSend() {
        final SenderDashboardStage stage = new SenderDashboardStage();
        stage.getController().setWelcomeInteract(wi);
        stage.display();
        ws.hide();
    }

    @Override
    public void onSelectReceive() {
        MobServerStage stage = new MobServerStage();
        stage.getController().setWelcomeInteract(wi);
        stage.display();
        ws.hide();
    }
}
