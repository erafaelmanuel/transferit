package io.ermdev.transferit.desktop.ui.welcome;

import javafx.stage.Stage;

public interface WelcomeInteract {

    void selectSend(Stage stage, WelcomeListener listener);

    void selectReceive(Stage stage, WelcomeListener listener);

    void selectOption(double x, double y, WelcomeListener listener);

    interface WelcomeListener {

        void onSend(Stage stage);

        void onReceive(Stage stage);

        void onUnableToSend();

        void onUnableToReceive();

        void onOption(double x, double y);
    }
}
