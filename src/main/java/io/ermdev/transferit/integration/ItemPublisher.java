package io.ermdev.transferit.integration;

import io.ermdev.transferit.integration.Publisher;

public interface ItemPublisher extends Publisher {

    void notifyBefore();

    void notifyAfter();
}
