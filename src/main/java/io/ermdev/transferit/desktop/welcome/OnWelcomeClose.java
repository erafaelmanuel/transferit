package io.ermdev.transferit.desktop.welcome;

@FunctionalInterface
public interface OnWelcomeClose {
    void onClose(boolean isServer);
}
