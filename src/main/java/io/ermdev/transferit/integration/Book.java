package io.ermdev.transferit.integration;

public class Book<T> {

    private final T t;

    public Book(T t) {
        this.t = t;
    }

    public T getContent() {
        return t;
    }
}
