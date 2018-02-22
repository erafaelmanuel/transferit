package io.ermdev.transferit.integration;

import java.util.HashSet;
import java.util.Set;

public abstract class Publisher {

    private Set<Subscriber> subscribers = new HashSet<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifyAll(final Book book) {
        subscribers.parallelStream().forEach(subscriber -> subscriber.release(book));
    }
}
