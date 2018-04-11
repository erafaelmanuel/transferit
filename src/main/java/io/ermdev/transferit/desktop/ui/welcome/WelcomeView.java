package io.ermdev.transferit.desktop.ui.welcome;

public interface WelcomeView {

    void navigateSend();

    void navigateReceive();

    void onErrorSend();

    void onErrorReceive();
}
