package io.ermdev.transferit;

import io.ermdev.transferit.local.client.Client;
import io.ermdev.transferit.local.server.Server;
import io.ermdev.transferit.ui.callback.OnWelcomeClose;
import io.ermdev.transferit.ui.stage.MainStage;
import io.ermdev.transferit.ui.stage.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application implements OnWelcomeClose {

    public static void main(String args[]) {
        launch(args);
//        System.out.print("Send or Receive? (type s/r) : ");
//        String input = new Scanner(System.in).next();
//        if (input.equalsIgnoreCase("r")) {
//            Server server = new Server(23411);
//            server.openConnection();
//        } else if(input.equalsIgnoreCase("s")) {
//            Client client = new Client();
//            client.findConnection();
//        }
//        System.out.println("Shutting down!");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WelcomeStage welcomeStage = new WelcomeStage(this);
        welcomeStage.show();
    }

    @Override
    public void onClose(boolean isServer) {
        if(isServer) {
            Server server = new Server(23411);
            server.openConnection();
        } else {
            Client client = new Client();
            client.findConnection();
        }
    }
}
