package io.ermdev.transferit.core;

import java.io.InputStream;

public interface Server {

    void open();

    void accept();

    void reject();

    void stop();

    @Deprecated
    void receiveFile(InputStream inputStream);
}
