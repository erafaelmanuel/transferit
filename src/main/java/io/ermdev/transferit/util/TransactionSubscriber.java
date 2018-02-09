package io.ermdev.transferit.util;

public interface TransactionSubscriber extends Subscriber {

    void update(int transfer);
}
