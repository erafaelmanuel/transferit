package io.ermdev.transferit;

public interface Server {

    void findConnection();

    void accept();

    void reject();

    void keepAlive();

    void stop();
}
