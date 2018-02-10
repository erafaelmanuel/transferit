package io.ermdev.transferit.local.server;

public class Sender {

    private String host;
    private boolean enabled;

    public Sender(String host, boolean enabled) {
        this.host = host;
        this.enabled = enabled;
    }

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
}
