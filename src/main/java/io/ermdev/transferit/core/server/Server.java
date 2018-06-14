package io.ermdev.transferit.core.server;

public interface Server {

    void open();

    void stop();

    void accept();

    void reject();
}
