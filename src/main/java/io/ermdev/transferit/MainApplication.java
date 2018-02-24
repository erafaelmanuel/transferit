package io.ermdev.transferit;

import io.ermdev.transferit.desktop.stage.ClientStage;
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
            ClientStage clientStage = new ClientStage(this);
            clientStage.show();
//            MobClientStage clientStage = new MobClientStage(this);
//            clientStage.getController().setWelcomeStage(welcomeStage);
//            clientStage.show();
        }
    }
}
