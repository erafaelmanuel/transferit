package io.ermdev.transferit.local.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class Client {

    private String HOST;
    private String FILE_NAME = "File.xyz";

    private Socket socket;

    public Client(String HOST) {
        try {
            this.HOST = HOST;
            socket = new Socket(HOST, 23411);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findConnection() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                byte buffer[] = new byte[(int) file.length()];

                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(buffer, 0, buffer.length);

                BufferedOutputStream writer = new BufferedOutputStream(socket.getOutputStream());
                writer.write(buffer, 0, buffer.length);
                writer.flush();
                writer.close();
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
