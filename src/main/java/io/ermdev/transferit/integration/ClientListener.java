package io.ermdev.transferit.integration;

@FunctionalInterface
public interface ClientListener {
    void onTransfer(double count);
}
