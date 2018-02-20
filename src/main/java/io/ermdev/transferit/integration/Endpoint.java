package io.ermdev.transferit.integration;

import java.util.HashSet;
import java.util.Set;

public class Endpoint implements Publisher {

    private String host;

    private int port;

    private boolean connected;

    public Set<Subscriber> subscribers = new HashSet<>();

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
        notifySubscriber();
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
    public void notifySubscriber() {
        subscribers.parallelStream().forEach(subscriber -> subscriber.update(connected));
    }
}
