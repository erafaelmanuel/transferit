package io.ermdev.transferit.arch;

public interface ItemSubscriber extends Subscriber {

    void beforeItemRelease();

    void afterItemRelease();
}
