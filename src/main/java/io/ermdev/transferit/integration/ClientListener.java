package io.ermdev.transferit.integration;

public interface ClientListener {

    void onStart();

    void onUpdate(double n);

    void onComplete(double total);
}
