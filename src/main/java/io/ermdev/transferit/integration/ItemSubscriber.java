package io.ermdev.transferit.integration;

public interface ItemSubscriber extends Subscriber {

    void beforeItemRelease();

    void afterItemRelease();
}
