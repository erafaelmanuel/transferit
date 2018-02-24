package io.ermdev.transferit.integration;

public interface InteractiveClient {

    ItemClientListener getListener();

    void setListener(ItemClientListener clientListener);
}
