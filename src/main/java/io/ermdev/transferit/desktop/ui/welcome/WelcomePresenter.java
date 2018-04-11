package io.ermdev.transferit.desktop.ui.welcome;

import javafx.stage.Stage;

public interface WelcomePresenter {

    void clickSend(Stage stage);

    void clickReceive(Stage stage);

    void clickOption(double x, double y);
}
