package io.ermdev.transferit.integration;

import java.io.File;
import java.net.Socket;

public interface Client {

    void connect() throws TcpException;

    void disconnect() throws TcpException;

    Socket newSocket() throws TcpException;

    void sendFile(File file);
}
