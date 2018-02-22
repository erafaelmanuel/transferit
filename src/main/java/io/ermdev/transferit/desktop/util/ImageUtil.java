package io.ermdev.transferit.desktop.util;

public class ImageUtil {

    public static String thumbnail(String path) {
        String extension = "";

        int i = path.lastIndexOf('.');
        int p = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

        if (i > p) {
            extension = path.substring(i + 1);
        }
        if (extension.equalsIgnoreCase("mp3")) {
            return "/image/mp3.png";
        } else if (extension.equalsIgnoreCase("jpg")) {
            return "/image/jpg.png";
        } else if (extension.equalsIgnoreCase("png")) {
            return "/image/png.png";
        } else if (extension.equalsIgnoreCase("mp4")) {
            return "/image/mp4.png";
        }
        else {
            return "/image/file.png";
        }
    }
}