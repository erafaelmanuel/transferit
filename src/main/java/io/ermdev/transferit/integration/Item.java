package io.ermdev.transferit.integration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Item implements Publisher {

    private int index;

    private String name;

    private double size;

    private double progress;

    private String image;

    private Set<Subscriber> subscribers = new HashSet<>();

    public Item(File file) {
        if (file.exists()) {
            setName(file.getName());
            setSize(file.length());
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
        notifyAll(new Book<>(progress));
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifyAll(final Book book) {
        subscribers.parallelStream().forEach(subscriber -> subscriber.release(book));
    }
}
