package io.ermdev.transferit.integration;

public interface ProtocolListener {

    void onCreate();

    void onFile(String name, long size);
}
