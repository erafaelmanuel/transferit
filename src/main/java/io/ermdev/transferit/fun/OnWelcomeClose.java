package io.ermdev.transferit.fun;

@FunctionalInterface
public interface OnWelcomeClose {

    void onClose(boolean isServer);
}
