package io.ermdev.transferit.core.arch;

public interface Publisher {

    void subscribe(final Subscriber subscriber);

    void unsubscribe(final Subscriber subscriber);

    void notifyAll(final Book book);
}
