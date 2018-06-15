package io.ermdev.transferit.core.arch;

public class Book<T> {

    private final T t;

    public Book(final T t) {
        this.t = t;
    }

    public T getContent() {
        return t;
    }
}
