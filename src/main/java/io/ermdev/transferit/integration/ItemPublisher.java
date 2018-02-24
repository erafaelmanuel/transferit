package io.ermdev.transferit.integration;

public interface ItemPublisher {

    void subscribe(ItemSubscriber subscriber);

    void unsubscribe(ItemSubscriber subscriber);

    void notifyAll(final Book book);

    void notifyBefore();

    void notifyAfter();
}
