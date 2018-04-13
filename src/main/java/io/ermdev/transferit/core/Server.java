package io.ermdev.transferit.core;

public interface Server {

    void open();

    void stop();

    void accept();

    void reject();
}
