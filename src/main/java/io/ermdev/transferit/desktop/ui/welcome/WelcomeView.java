package io.ermdev.transferit.desktop.ui.welcome;

import javafx.stage.Stage;

public interface WelcomeView {

    void navigateSend(Stage stage);

    void navigateReceive(Stage stage);

    void onErrorSend();

    void onErrorReceive();

    void showOptionMenu(double x, double y);
}
