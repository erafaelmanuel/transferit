package io.ermdev.transferit.integration.protocol;

import io.ermdev.transferit.integration.Endpoint;

import java.net.ServerSocket;

public class LinkImpl implements Link {

    private ServerSocket server1;

    private ServerSocket server2;

    private final Thread manager;

    private volatile boolean isDone;

    private Endpoint endpoint;

    public LinkImpl(Endpoint endpoint) {
        this.endpoint = endpoint;
        manager = new Thread(new ManagerTask());
    }

    @Override
    public synchronized void start() {
        if (isDone) {
            manager.start();
        }
    }

    @Override
    public synchronized void stop() {
        if (!isDone) {
            isDone = true;
        }
    }

    class ManagerTask implements Runnable {
        @Override
        public void run() {
            isDone = false;
            while (!isDone) {

            }
        }
    }
}
