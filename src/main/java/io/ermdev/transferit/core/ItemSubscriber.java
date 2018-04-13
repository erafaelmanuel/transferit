package io.ermdev.transferit.core;

import io.ermdev.transferit.arch.Subscriber;

public interface ItemSubscriber extends Subscriber {

    void beforeItemRelease();

    void afterItemRelease();
}
