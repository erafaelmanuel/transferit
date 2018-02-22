package io.ermdev.transferit.desktop.util;

public class TraficUtil {

    public static String size(Long buffer) {
        if ((buffer / 1000000000) > 0) {
            return (buffer / 1000000000 + " GB");
        } else if ((buffer / 1000000) > 0) {
            return (buffer / 1000000 + " MB");
        } else if ((buffer / 1000) > 0) {
            return (buffer / 1000 + " KB");
        } else {
            return (buffer + " bytes");
        }
    }
}
