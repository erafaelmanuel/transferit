package io.ermdev.transferit;

import io.ermdev.transferit.local.MyProtocol;
import io.ermdev.transferit.local.server.Server;
import io.ermdev.transferit.ui.callback.OnWelcomeClose;
import io.ermdev.transferit.ui.stage.ClientStage;
import io.ermdev.transferit.ui.stage.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application implements OnWelcomeClose {

    public static void main(String args[]) {
        MyProtocol protocol = new MyProtocol();
        protocol.addFile(new File("1.jpg"));
        protocol.addFile(new File("2.jpg"));
        protocol.addFile(new File("3.jpg"));
        System.out.println(protocol.getHeader());
        //launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WelcomeStage welcomeStage = new WelcomeStage(this);
        welcomeStage.show();
    }

    @Override
    public void onClose(boolean isServer) {
        if (isServer) {
            Server server = new Server(23411);
            server.openConnection();
        } else {
            ClientStage clientStage = new ClientStage(this);
            clientStage.show();
        }
    }
}
