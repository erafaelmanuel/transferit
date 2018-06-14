package io.ermdev.transferit.core.arch;

public class Book<T> {

    private final T t;

    public Book(T t) {
        this.t = t;
    }

    public T getContent() {
        return t;
    }
}
