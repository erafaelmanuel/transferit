package io.ermdev.transferit.core;

public interface ProtocolListener {

    void onCreate();

    void onFile(String name, long size);

    void onScan();
}
