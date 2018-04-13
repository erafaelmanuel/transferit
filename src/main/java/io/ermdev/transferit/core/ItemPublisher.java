package io.ermdev.transferit.core;

import io.ermdev.transferit.arch.Book;

public interface ItemPublisher {

    void subscribe(ItemSubscriber subscriber);

    void unsubscribe(ItemSubscriber subscriber);

    void notifyAll(final Book book);

    void notifyBefore();

    void notifyAfter();
}
