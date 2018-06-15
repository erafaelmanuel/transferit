package io.ermdev.transferit.core.protocol;

import io.ermdev.transferit.core.arch.Book;
import io.ermdev.transferit.core.arch.ItemPublisher;
import io.ermdev.transferit.core.arch.ItemSubscriber;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Item implements ItemPublisher {

    private String name;

    private double size;

    private double progress;

    private String image;

    private File file;

    private Set<ItemSubscriber> subscribers = new HashSet<>();

    public Item(File file) {
        setName(file.getName());
        setSize(file.length());
        setFile(file);
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void subscribe(ItemSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(ItemSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifyAll(final Book book) {
        subscribers.parallelStream().forEach(subscriber -> subscriber.onRelease(book));
    }

    @Override
    public void notifyBefore() {
        subscribers.parallelStream().forEach(ItemSubscriber::beforeItemRelease);
    }

    @Override
    public void notifyAfter() {
        subscribers.parallelStream().forEach(ItemSubscriber::afterItemRelease);
    }
}