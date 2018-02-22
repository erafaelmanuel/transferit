package io.ermdev.transferit.integration;

public class Endpoint extends Publisher {

    private String host;

    private int port;

    private boolean connected;

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
}
