package io.ermdev.transferit.core.arch;

public interface Publisher {

    void subscribe(Subscriber subscriber);

    void unsubscribe(Subscriber subscriber);

    void notifyAll(final Book book);
}
