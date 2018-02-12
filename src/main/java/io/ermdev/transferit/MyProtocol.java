package io.ermdev.transferit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyProtocol {

    private List<File> files = new ArrayList<>();

    public void addFile(File file) {
        files.add(file);
    }

    public String getHeader() {
        final int size = files.size();
        final StringBuilder builder = new StringBuilder();

        builder.append(size);
        builder.append(":");
        for(File file : files) {
            builder.append(file.getName());
            builder.append("#");
        }
        return builder.toString();
    }

    public List<File> getBody() {
        return files;
    }
}
