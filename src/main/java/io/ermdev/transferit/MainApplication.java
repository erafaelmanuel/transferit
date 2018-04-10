package io.ermdev.transferit;

import io.ermdev.transferit.desktop.ui.client.SenderDashboardStage;
import io.ermdev.transferit.desktop.ui.server.MobServerStage;
import io.ermdev.transferit.desktop.ui.welcome.WelcomeInteract;
import io.ermdev.transferit.desktop.ui.welcome.WelcomeInteractImpl;
import io.ermdev.transferit.desktop.ui.welcome.WelcomeStage;
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
        wi = new WelcomeInteractImpl(this);

        ws.getController().setWelcomeInteract(wi);
        wi.display(0, 0);
    }

    @Override
    public void onShow(double x, double y) {
        ws.show(x, y);
    }

    @Override
    public void onSelectSend() {
        final SenderDashboardStage sds = new SenderDashboardStage();
        sds.getController().setWelcomeInteract(wi);
        sds.display(ws.getX(), ws.getY());
        ws.hide();
    }

    @Override
    public void onSelectReceive() {
        MobServerStage stage = new MobServerStage();
        stage.getController().setWelcomeInteract(wi);
        stage.display(ws.getX(), ws.getY());
        ws.hide();
    }
}
