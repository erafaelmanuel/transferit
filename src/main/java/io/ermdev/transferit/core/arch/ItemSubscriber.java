package io.ermdev.transferit.core.arch;

public interface ItemSubscriber extends Subscriber {

    void beforeItemRelease();

    void afterItemRelease();
}
