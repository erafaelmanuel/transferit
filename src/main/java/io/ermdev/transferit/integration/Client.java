package io.ermdev.transferit.integration;

public interface Client {

    void connect() throws TcpException;

    void disconnect() throws TcpException;
}
