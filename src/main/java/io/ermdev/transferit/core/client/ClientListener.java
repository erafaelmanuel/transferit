package io.ermdev.transferit.core.client;

import io.ermdev.transferit.core.protocol.Item;

public interface ClientListener {

    void onSendFileStart(Item item);

    void onSendFileUpdate(Item item, double n);

    void onSendFileComplete(Item item);

    void onTransferSpeed(String speed);
}
