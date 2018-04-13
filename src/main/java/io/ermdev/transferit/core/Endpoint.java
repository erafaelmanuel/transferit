package io.ermdev.transferit.core;

import io.ermdev.transferit.arch.Book;
import io.ermdev.transferit.arch.Publisher;
import io.ermdev.transferit.arch.Subscriber;

import java.util.HashSet;
import java.util.Set;

public class Endpoint implements Publisher {

    private String host;

    private int port;

    private boolean connected;

    private Set<Subscriber> subscribers = new HashSet<>();

    public Endpoint() {}

    public Endpoint(int port) {
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
