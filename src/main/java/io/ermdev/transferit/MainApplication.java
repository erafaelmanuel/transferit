package io.ermdev.transferit;

import io.ermdev.transferit.desktop.client.MobClient1Stage;
import io.ermdev.transferit.desktop.server.MobServerStage;
import io.ermdev.transferit.desktop.welcome.WelcomeStage;
import io.ermdev.transferit.desktop.welcome.OnWelcomeClose;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application implements OnWelcomeClose {

    private WelcomeStage welcomeStage;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        welcomeStage = new WelcomeStage(this);
        welcomeStage.show();
    }

    @Override
    public void onClose(boolean isServer) {
        if (isServer) {
            try {
                MobServerStage serverStage = new MobServerStage(this);
                serverStage.getController().setWelcomeStage(welcomeStage);
                serverStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MobClient1Stage clientStage = new MobClient1Stage(this);
            clientStage.getController().setWelcomeStage(welcomeStage);
            clientStage.show();
        }
    }
}
