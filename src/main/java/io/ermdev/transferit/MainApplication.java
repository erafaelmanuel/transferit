package io.ermdev.transferit;

import io.ermdev.transferit.desktop.ui.welcome.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private WelcomeStage ws;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ws = new WelcomeStage();
        ws.getController().setStage(ws);
        ws.show(0, 0);
    }
}
