package io.ermdev.transferit.ui.callback;

@FunctionalInterface
public interface OnWelcomeClose {

    void onClose(boolean isServer);
}
