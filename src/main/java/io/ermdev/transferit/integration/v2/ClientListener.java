package io.ermdev.transferit.integration.v2;

import io.ermdev.transferit.integration.Item;

public interface ClientListener {

    int TIME_ITERVAL = 400;

    void onSendFileStart(Item item);

    void onSendFileUpdate(Item item, double n);

    void onSendFileComplete(Item item);

    void onTransferSpeed(String speed);
}
