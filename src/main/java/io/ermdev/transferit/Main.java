package io.ermdev.transferit;

import io.ermdev.transferit.local.server.Server;
import io.ermdev.transferit.ui.callback.OnWelcomeClose;
import io.ermdev.transferit.ui.stage.ClientStage;
import io.ermdev.transferit.ui.stage.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application implements OnWelcomeClose {

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WelcomeStage welcomeStage = new WelcomeStage(this);
        welcomeStage.show();
    }

    @Override
    public void onClose(boolean isServer) {
        if (isServer) {
            try {
                Server server = new Server(23411);
                server.channeling();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ClientStage clientStage = new ClientStage(this);
            clientStage.show();
        }
    }
}
