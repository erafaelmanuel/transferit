package io.ermdev.transferit.local.client;

import java.net.Socket;

public class Client {

    public Client() {
    }

    public void findConnection() {
        try {
            Socket socket = new Socket("localhost", 23411);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
