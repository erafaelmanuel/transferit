package io.ermdev.transferit.core.arch;

public interface ItemPublisher {

    void subscribe(ItemSubscriber subscriber);

    void unsubscribe(ItemSubscriber subscriber);

    void notifyAll(final Book book);

    void notifyBefore();

    void notifyAfter();
}
