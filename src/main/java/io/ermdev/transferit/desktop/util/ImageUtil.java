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
            return "/image/line/mp3.png";
        } else if (extension.equalsIgnoreCase("jpg")) {
            return "/image/line/jpg.png";
        } else if (extension.equalsIgnoreCase("png")) {
            return "/image/line/png.png";
        } else if (extension.equalsIgnoreCase("mp4")) {
            return "/image/line/mp4.png";
        } else if (extension.equalsIgnoreCase("zip")) {
            return "/image/line/zip.png";
        } else if (extension.equalsIgnoreCase("avi")) {
            return "/image/line/avi.png";
        } else if (extension.equalsIgnoreCase("css")) {
            return "/image/line/css.png";
        } else if (extension.equalsIgnoreCase("exe")) {
            return "/image/line/exe.png";
        } else if (extension.equalsIgnoreCase("html")) {
            return "/image/line/html.png";
        } else if (extension.equalsIgnoreCase("javascript")) {
            return "/image/line/javascript.png";
        } else if (extension.equalsIgnoreCase("txt")) {
            return "/image/line/txt.png";
        } else if (extension.equalsIgnoreCase("ai")) {
            return "/image/line/ai.png";
        } else if (extension.equalsIgnoreCase("csv")) {
            return "/image/line/csv.png";
        } else if (extension.equalsIgnoreCase("doc")) {
            return "/image/line/doc.png";
        } else if (extension.equalsIgnoreCase("fla")) {
            return "/image/line/fla.png";
        } else if (extension.equalsIgnoreCase("iso")) {
            return "/image/line/iso.png";
        } else if (extension.equalsIgnoreCase("ppt")) {
            return "/image/line/ppt.png";
        } else if (extension.equalsIgnoreCase("pdf")) {
            return "/image/lin/pdf.png";
        } else if (extension.equalsIgnoreCase("json-file")) {
            return "/image/line/json-file.png";
        } else if (extension.equalsIgnoreCase("svg")) {
            return "/image/line/svg.png";
        } else if (extension.equalsIgnoreCase("xml")) {
            return "/image/line/xml.png";
        } else if (extension.equalsIgnoreCase("xls")) {
            return "/image/line/xls.png";
        } else {
            return "/image/line/file.png";
        }
    }
}