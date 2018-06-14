package io.ermdev.transferit.core.client;

import io.ermdev.transferit.core.protocol.State;

import java.io.File;
import java.net.Socket;

public interface Client {

    void connect() throws ClientException;

    void disconnect();

    State getState();

    Socket newSocket() throws ClientException;

    void sendFile(File file);
}
