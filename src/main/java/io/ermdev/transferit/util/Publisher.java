package io.ermdev.transferit.util;

public interface Publisher {

    void subscribe(Subscriber subscriber);
    void unsubscribe(Subscriber subscriber);
    void notifySubscriber();
}
