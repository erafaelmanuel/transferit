package io.ermdev.transferit.fun;

@FunctionalInterface
public interface ClientListener {
    void onTransfer(double count);
}
