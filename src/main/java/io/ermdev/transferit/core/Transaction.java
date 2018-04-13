package io.ermdev.transferit.core;

import io.ermdev.transferit.desktop.util.TrafficUtil;

import java.io.File;

public class Transaction extends Item {

    private String percentage;
    private int index;

    public Transaction(File file) {
        super(file);
    }

    public String getPercentage() {
        if (percentage != null && percentage.equalsIgnoreCase("Completed")) {
            return percentage;
        }
        return percentage = (int) ((100 / getSize()) * getProgress()) + "%";
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFileSize() {
        return new TrafficUtil().size((long) getSize());
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public double getSize() {
        return super.getSize();
    }

    @Override
    public void setSize(double size) {
        super.setSize(size);
    }

    @Override
    public double getProgress() {
        return super.getProgress();
    }

    @Override
    public void setProgress(double progress) {
        super.setProgress(progress);
    }

    @Override
    public String getImage() {
        return super.getImage();
    }

    @Override
    public void setImage(String image) {
        super.setImage(image);
    }

    @Override
    public File getFile() {
        return super.getFile();
    }

    @Override
    public void setFile(File file) {
        super.setFile(file);
    }
}
