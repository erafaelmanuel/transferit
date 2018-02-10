package io.ermdev.transferit;

import io.ermdev.transferit.local.server.BasicServer;
import io.ermdev.transferit.local.server.Sender;
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
                BasicServer server = new BasicServer(23411);
                server.setConnection(new Sender());
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
