package io.ermdev.transferit.local.client;

import io.ermdev.transferit.util.Publisher;
import io.ermdev.transferit.util.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class Receiver implements Publisher {

    private String host;
    private int port;
    private boolean connected;

    private List<Subscriber> subscribers = new ArrayList<>();

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
        for (Subscriber subscriber : subscribers) {
            subscriber.update(connected);
        }
    }
}
