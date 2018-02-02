package io.ermdev.transferit.local.server;

import java.net.ServerSocket;

public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
    }
    private ServerSocket serverSocket;

    public void openConnection() {
        try {
            System.out.println("Waiting ...");
            serverSocket = new ServerSocket(port);
            serverSocket.accept();
            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
