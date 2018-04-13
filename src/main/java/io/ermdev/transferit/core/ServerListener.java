package io.ermdev.transferit.core;

import java.io.InputStream;

public interface ServerListener {

    void onInvite();

    void onNewFile(String name, long size);

    void onReceiveFile(InputStream inputStream);
}
