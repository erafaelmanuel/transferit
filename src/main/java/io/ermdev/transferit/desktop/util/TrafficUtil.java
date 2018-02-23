package io.ermdev.transferit.desktop.util;

import java.util.Locale;

public class TrafficUtil {

    public static String size(Long buffer) {
        if ((buffer / 1000000000) > 0) {
            return String.format(Locale.ENGLISH, "%.2f GB", buffer / 1000000000.0);
        } else if ((buffer / 1000000) > 0) {
            return String.format(Locale.ENGLISH, "%.2f MB", buffer / 1000000.0);
        } else if ((buffer / 1000) > 0) {
            return String.format(Locale.ENGLISH, "%.2f KB", buffer / 1000.0);
        } else {
            return (buffer + " bytes");
        }
    }
}
