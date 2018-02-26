package io.ermdev.transferit.desktop.util;

import java.util.Locale;

public class TrafficUtil {

    private String size;
    private String speed;
    private String round;

    public String size(long buffer) {
        if ((buffer / 1000000000) > 0) {
            return size = String.format(Locale.ENGLISH, "%.2f GB", buffer / 1000000000.0);
        } else if ((buffer / 1000000) > 0) {
            return size = String.format(Locale.ENGLISH, "%.2f MB", buffer / 1000000.0);
        } else if ((buffer / 1000) > 0) {
            return size = String.format(Locale.ENGLISH, "%.2f KB", buffer / 1000.0);
        } else {
            return size = (buffer + " bytes");
        }
    }

    public String speed(Long buffer) {
        if ((buffer / 1000000) > 0) {
            return speed = String.format(Locale.ENGLISH, "%.2f GB/s", ((double) buffer) / 1000000.0);
        } else if ((buffer / 1000) > 0) {
            return speed = String.format(Locale.ENGLISH, "%.2f MB/s", ((double) buffer) / 1000.0);
        } else {
            return speed = String.format(Locale.ENGLISH, "%.2f KB/s", ((double) buffer));
        }
    }

    public String round(Long buffer) {
        if ((buffer / 1000000000) > 0) {
            return round = String.format(Locale.ENGLISH, "%.1f", buffer / 1000000000.0);
        } else if ((buffer / 1000000) > 0) {
            return round = String.format(Locale.ENGLISH, "%.1f", buffer / 1000000.0);
        } else if ((buffer / 1000) > 0) {
            return round = String.format(Locale.ENGLISH, "%.1f", buffer / 1000.0);
        } else {
            return round = (buffer + " bytes");
        }
    }

    public String getLastSize() {
        return size;
    }

    public String getLastSpeed() {
        return speed;
    }

    public String getLastRound() {
        return round;
    }
}
