package io.ermdev.transferit.integration;

import java.io.File;

public class Item {

    private static int index = 1;

    private String name;

    private double size;

    private double progress;

    public Item(File file) {
        setName(file.getName());
        setSize(file.length());
    }

    public int getIndex() {
        return index;
    }

    public void resetIndex() {
        index = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
