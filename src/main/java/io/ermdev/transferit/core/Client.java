package io.ermdev.transferit.core;

import java.io.File;
import java.net.Socket;

public interface Client {

    void connect() throws ClientException;

    void disconnect();

    Endpoint getEndpoint();

    Socket newSocket() throws ClientException;

    void sendFile(File file);
}
