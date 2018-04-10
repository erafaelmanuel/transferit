package io.ermdev.transferit.desktop.ui.welcome;

public interface WelcomeInteract {

    void display(double x, double y);

    void selectSend();

    void selectReceive();

    interface WelcomeListener {

        void onShow(double x, double y);

        void onSelectSend();

        void onSelectReceive();
    }
}
