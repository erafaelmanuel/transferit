package io.ermdev.transferit.desktop.welcome;

@FunctionalInterface
public interface WelcomeListener {
    void onClose(boolean isServer);
}
