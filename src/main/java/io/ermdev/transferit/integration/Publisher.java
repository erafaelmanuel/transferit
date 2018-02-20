package io.ermdev.transferit.integration;

import java.util.HashSet;
import java.util.Set;

public class Publisher {

    private Set<Subscriber> subscribers = new HashSet<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifyAll(boolean status) {
        subscribers.parallelStream().forEach(subscriber -> subscriber.update(status));
    }
}
