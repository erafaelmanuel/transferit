package io.ermdev.transferit.desktop.ui.welcome;

import javafx.stage.Stage;

public class WelcomeInteractImpl implements WelcomeInteract {

    @Override
    public void selectSend(Stage stage, WelcomeListener listener) {
        if (true) {
            listener.onSend(stage);
        } else {
            listener.onUnableToSend();
        }
    }

    @Override
    public void selectReceive(Stage stage, WelcomeListener listener) {
        if (false) {
            listener.onReceive(stage);
        } else {
            listener.onUnableToReceive();
        }
    }

    @Override
    public void selectOption(double x, double y, WelcomeListener listener) {
        listener.onOption(x, y);
    }

}
