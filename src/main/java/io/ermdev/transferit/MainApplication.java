package io.ermdev.transferit;

import io.ermdev.transferit.desktop.ui.option.SettingStage;
import io.ermdev.transferit.desktop.ui.welcome.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        final WelcomeStage ws = new WelcomeStage();
//        ws.display(0, 0);

        SettingStage settingStage = new SettingStage();
        settingStage.show();
    }
}
