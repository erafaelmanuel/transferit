package io.ermdev.transferit.desktop.ui.welcome;

public interface WelcomeInteract {

    void setDisplay(double x, double y);

    interface WelcomeListener {

        void onShow(double x, double y);

        void onSelectSend();

        void onSelectReceive();
    }
}
