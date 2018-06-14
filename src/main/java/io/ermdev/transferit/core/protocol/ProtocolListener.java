package io.ermdev.transferit.core.protocol;

public interface ProtocolListener {

    void onCreate();

    void onFile(String name, long size);

    void onScan();
}
