package io.ermdev.transferit.local.client;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;

    public Client(final String host, final int port) throws IOException {
        socket = new Socket(host, port);
    }

    public void send(File file) {
        try {
            if (file.exists()) {
                byte buffer[] = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(buffer, 0, buffer.length);

                BufferedOutputStream writer = new BufferedOutputStream(socket.getOutputStream());
                writer.write(buffer, 0, buffer.length);
                writer.flush();
                //writer.close();
            } else {
                System.out.println("Invalid file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
