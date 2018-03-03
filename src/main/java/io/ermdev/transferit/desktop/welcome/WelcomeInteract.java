package io.ermdev.transferit.desktop.welcome;

public interface WelcomeInteract {

    void setDisplay();

    interface WelcomeListener {

        void onShow();

        void onSelectSend();

        void onSelectReceive();
    }
}
