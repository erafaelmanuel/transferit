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
            return "/image/extension/mp3.png";
        } else if (extension.equalsIgnoreCase("jpg")) {
            return "/image/extension/jpg.png";
        } else if (extension.equalsIgnoreCase("png")) {
            return "/image/extension/png.png";
        } else if (extension.equalsIgnoreCase("mp4")) {
            return "/image/extension/mp4.png";
        } else if (extension.equalsIgnoreCase("zip")) {
            return "/image/extension/zip.png";
        } else if (extension.equalsIgnoreCase("avi")) {
            return "/image/extension/avi.png";
        } else if (extension.equalsIgnoreCase("css")) {
            return "/image/extension/css.png";
        } else if (extension.equalsIgnoreCase("exe")) {
            return "/image/extension/exe.png";
        } else if (extension.equalsIgnoreCase("html")) {
            return "/image/extension/html.png";
        } else if (extension.equalsIgnoreCase("javascript")) {
            return "/image/extension/javascript.png";
        } else if (extension.equalsIgnoreCase("txt")) {
            return "/image/extension/txt.png";
        } else if (extension.equalsIgnoreCase("ai")) {
            return "/image/extension/ai.png";
        } else if (extension.equalsIgnoreCase("csv")) {
            return "/image/extension/csv.png";
        } else if (extension.equalsIgnoreCase("doc")) {
            return "/image/extension/doc.png";
        } else if (extension.equalsIgnoreCase("fla")) {
            return "/image/extension/fla.png";
        } else if (extension.equalsIgnoreCase("iso")) {
            return "/image/extension/iso.png";
        } else if (extension.equalsIgnoreCase("ppt")) {
            return "/image/extension/ppt.png";
        } else if (extension.equalsIgnoreCase("pdf")) {
            return "/image/extension/pdf.png";
        } else if (extension.equalsIgnoreCase("json-file")) {
            return "/image/extension/json-file.png";
        } else if (extension.equalsIgnoreCase("svg")) {
            return "/image/extension/svg.png";
        } else if (extension.equalsIgnoreCase("xml")) {
            return "/image/extension/xml.png";
        } else if (extension.equalsIgnoreCase("xls")) {
            return "/image/extension/xls.png";
        } else {
            return "/image/extension/file.png";
        }
    }
}