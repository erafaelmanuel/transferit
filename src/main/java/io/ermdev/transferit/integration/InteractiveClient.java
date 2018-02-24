package io.ermdev.transferit.integration;

public interface InteractiveClient {

    ClientListener getListener();

    void setListener(ClientListener clientListener);
}
