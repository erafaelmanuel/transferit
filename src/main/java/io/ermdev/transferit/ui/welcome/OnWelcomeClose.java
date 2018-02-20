package io.ermdev.transferit.ui.welcome;

@FunctionalInterface
public interface OnWelcomeClose {
    void onClose(boolean isServer);
}
