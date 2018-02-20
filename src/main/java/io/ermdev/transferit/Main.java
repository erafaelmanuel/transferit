package io.ermdev.transferit;

import io.ermdev.transferit.fun.OnWelcomeClose;
import io.ermdev.transferit.ui.client.Client2Stage;
import io.ermdev.transferit.ui.client.ClientStage;
import io.ermdev.transferit.ui.stage.ServerStage;
import io.ermdev.transferit.ui.stage.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application implements OnWelcomeClose {

    private WelcomeStage welcomeStage;
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
            clientStage.getClientController().setWelcomeStage(welcomeStage);
            clientStage.show();
        }
    }
}
