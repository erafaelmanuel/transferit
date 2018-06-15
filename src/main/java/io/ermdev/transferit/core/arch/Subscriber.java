package io.ermdev.transferit.core.arch;

public interface Subscriber {

    void onRelease(Book<?> book);
}
