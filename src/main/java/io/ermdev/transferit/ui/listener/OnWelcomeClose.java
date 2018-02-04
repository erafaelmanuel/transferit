package io.ermdev.transferit.ui.listener;

@FunctionalInterface
public interface OnWelcomeClose {

    void onClose(boolean isServer);
}
