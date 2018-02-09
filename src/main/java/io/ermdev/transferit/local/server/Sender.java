package io.ermdev.transferit.local.server;

public class Sender {

    private String address;
    private boolean enabled;

    public Sender(String address, boolean enabled) {
        this.address = address;
        this.enabled = enabled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
