package io.ermdev.transferit.integration;

public interface ClientListener1 {

    void onStart();

    void onUpdate(double n);

    void onComplete(double total);
}
