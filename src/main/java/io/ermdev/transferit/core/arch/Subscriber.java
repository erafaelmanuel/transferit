package io.ermdev.transferit.core.arch;

public interface Subscriber {

    void release(Book<?> book);
}
