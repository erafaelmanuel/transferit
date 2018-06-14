package io.ermdev.transferit.core.client;

import io.ermdev.transferit.core.protocol.State;

import java.io.File;

public interface Client {

    void connect() throws ClientException;

    void disconnect();

    State getState();

    void sendFile(File file);
}
