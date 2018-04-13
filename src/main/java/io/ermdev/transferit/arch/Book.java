package io.ermdev.transferit.arch;

public class Book<T> {

    private final T t;

    public Book(T t) {
        this.t = t;
    }

    public T getContent() {
        return t;
    }
}
