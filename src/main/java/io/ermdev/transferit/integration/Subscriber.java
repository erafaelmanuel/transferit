package io.ermdev.transferit.integration;

public interface Subscriber {

    void release(Book<?> book);
}
