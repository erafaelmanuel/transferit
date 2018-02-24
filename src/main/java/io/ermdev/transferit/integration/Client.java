package io.ermdev.transferit.integration;

import java.io.File;
import java.net.Socket;

public interface Client {

    void connect() throws ClientException;

    void disconnect();

    Socket newSocket() throws ClientException;

    void sendFile(File file);
}
