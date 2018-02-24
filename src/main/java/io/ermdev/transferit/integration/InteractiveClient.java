package io.ermdev.transferit.integration;

import io.ermdev.transferit.integration.v2.ClientListener;

public interface InteractiveClient {

    ClientListener getListener();

    void setListener(ClientListener clientListener);
}
