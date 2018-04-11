package io.ermdev.transferit.desktop.ui.welcome.v2;

public interface WelcomeInteract {

    void selectSend(WelcomeListener listener);

    void selectReceive(WelcomeListener listener);

    interface WelcomeListener {

        void onSend();

        void onReceive();

        void onUnableToSend();

        void onUnableToReceive();
    }
}
