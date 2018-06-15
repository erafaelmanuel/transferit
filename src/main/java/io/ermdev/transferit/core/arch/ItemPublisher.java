package io.ermdev.transferit.core.arch;

public interface ItemPublisher {

    void subscribe(final ItemSubscriber subscriber);

    void unsubscribe(final ItemSubscriber subscriber);

    void notifyAll(final Book book);

    void notifyBefore();

    void notifyAfter();
}
