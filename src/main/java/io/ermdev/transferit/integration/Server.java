package io.ermdev.transferit.integration;

import java.io.InputStream;

public interface Server {

    void open();

    void accept();

    void reject();

    void stop();

    @Deprecated
    void receiveFile(InputStream inputStream);
}
