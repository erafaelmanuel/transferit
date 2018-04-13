package io.ermdev.transferit.core;

public interface InteractiveClient {

    ClientListener getListener();

    void setListener(ClientListener clientListener);
}
