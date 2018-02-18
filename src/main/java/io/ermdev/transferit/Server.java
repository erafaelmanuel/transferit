package io.ermdev.transferit;

import io.ermdev.transferit.fun.ServerListener;

public interface Server {

    void addListener(ServerListener serverListener);

    void findConnection();

    void accept();

    void reject();

    void keepAlive();

    void stop();
}
