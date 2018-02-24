package io.ermdev.transferit.integration;

import io.ermdev.transferit.integration.Subscriber;

public interface ItemSubscriber extends Subscriber {

    void beforeItemRelease();

    void afterItemRelease();
}
