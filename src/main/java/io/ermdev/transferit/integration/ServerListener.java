package io.ermdev.transferit.integration;

import java.io.InputStream;

public interface ServerListener {

    void onInvite();

    void onNewFile(String name, long size);

    void onReceiveFile(InputStream inputStream);
}
