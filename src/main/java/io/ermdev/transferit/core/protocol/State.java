package io.ermdev.transferit.core.protocol;

import io.ermdev.transferit.core.arch.Book;
import io.ermdev.transferit.core.arch.Publisher;
import io.ermdev.transferit.core.arch.Subscriber;

import java.util.HashSet;
import java.util.Set;

public class State implements Publisher {

    private String host;

    private int port;

    private boolean connected;

    private Set<Subscriber> subscribers = new HashSet<>();

    public State() {}

    public State(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        notifyAll(new Book<>(connected));
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifyAll(final Book book) {
        subscribers.parallelStream().forEach(subscriber -> subscriber.release(book));
    }
}
