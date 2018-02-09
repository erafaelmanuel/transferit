package io.ermdev.transferit.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyProtocol {

    private String header;
    private List<File> body = new ArrayList<>();
    private int size;

    public String getHeader() {
        return header;
    }

    public List<File> getBody() {
        return body;
    }
}
