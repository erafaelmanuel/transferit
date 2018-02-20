package io.ermdev.transferit.integration;

public interface Server {

    void addListener(ServerListener serverListener);

    void findConnection();

    void accept();

    void reject();

    void keepAlive();

    void stop();
}
