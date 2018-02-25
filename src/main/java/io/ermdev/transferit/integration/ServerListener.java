package io.ermdev.transferit.integration;

public interface ServerListener {

    void onInvite();

    void onNewFile(String name, long size);
}
