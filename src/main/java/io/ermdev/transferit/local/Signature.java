package io.ermdev.transferit.local;

public class Signature {

    private String ip;
    private boolean enabled;

    public Signature(String ip, boolean enabled) {
        this.ip = ip;
        this.enabled = enabled;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
