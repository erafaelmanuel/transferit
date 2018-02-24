package io.ermdev.transferit.integration.v2;

import io.ermdev.transferit.desktop.util.TrafficUtil;
import io.ermdev.transferit.integration.Book;
import io.ermdev.transferit.integration.Item;
import io.ermdev.transferit.integration.Subscriber;

import java.io.File;

public class Transaction extends Item {

    private String percentage;

    private String fileSize;

    public Transaction(File file) {
        super(file);
    }

    public String getPercentage() {
        if(percentage != null && percentage.equalsIgnoreCase("Completed")) {
            return percentage;
        }
        return percentage = (int)((100 / getSize()) * getProgress()) + "%";
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
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

    @Override
    public void subscribe(Subscriber subscriber) {
        super.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        super.unsubscribe(subscriber);
    }

    @Override
    public void notifyAll(Book book) {
        super.notifyAll(book);
    }
}
