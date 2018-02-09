package io.ermdev.transferit.local.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

public class BasicClient {

    private Socket socket;

    public BasicClient(final String host, final int port) throws IOException {
        socket = new Socket(host, port);
    }

    public void send(File file) {
        try {
            if (file.exists()) {
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                DataOutputStream dos = new DataOutputStream(bos);
                dos.writeUTF(file.getName());
                Files.copy(file.toPath(), dos);
                dos.close();
            } else {
                System.out.println("Invalid file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public Socket getSocket() {
        return socket;
    }
}
