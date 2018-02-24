package io.ermdev.transferit.integration;

import java.io.InputStream;

public interface Server {

    void open();

    void accept();

    void stop();

    void receiveFile(InputStream inputStream);
}
