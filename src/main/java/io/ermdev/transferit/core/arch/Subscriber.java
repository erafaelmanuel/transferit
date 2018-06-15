package io.ermdev.transferit.core.arch;

public interface Subscriber {

    void onRelease(final Book<?> book);
}
