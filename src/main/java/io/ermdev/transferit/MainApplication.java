package io.ermdev.transferit;

import io.ermdev.transferit.desktop.client.Client2Stage;
import io.ermdev.transferit.desktop.client.ClientStage;
import io.ermdev.transferit.desktop.stage.ServerStage;
import io.ermdev.transferit.desktop.stage.WelcomeStage;
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
                ServerStage serverStage = new ServerStage(this);
                serverStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Client2Stage clientStage = new Client2Stage(this);
            clientStage.getClientController().setWelcomeStage(welcomeStage);
            clientStage.show();
        }
    }
}
