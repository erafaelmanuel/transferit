package io.ermdev.transferit.arch;

public interface Subscriber {

    void release(Book<?> book);
}
