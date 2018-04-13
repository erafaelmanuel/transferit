package io.ermdev.transferit.core;

public interface ClientListener {

    void onSendFileStart(Item item);

    void onSendFileUpdate(Item item, double n);

    void onSendFileComplete(Item item);

    void onTransferSpeed(String speed);
}
