package io.ermdev.transferit;

import io.ermdev.transferit.util.Publisher;
import io.ermdev.transferit.util.Subscriber;

import java.util.HashSet;
import java.util.Set;

public class Sender implements Publisher {

    private String host;
    private boolean enabled;

    private Set<Subscriber> subscribers = new HashSet<>();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {

    }

    @Override
    public void notifySubscriber() {
        subscribers.parallelStream().forEach(subscriber -> subscriber.update(enabled));
    }
}
