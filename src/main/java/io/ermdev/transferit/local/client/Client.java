package io.ermdev.transferit.local.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client() {
    }

    public void findConnection() {
        try {
            Socket socket = new Socket("localhost", 23411);

            File file = new File("sample.mp3");

            System.out.println("File is exist : " + file.exists());

            byte buffer[] = new byte[(int) file.length()];

            System.out.println(buffer.length);

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            int tae = bis.read(buffer, 0, buffer.length);

            System.out.println(tae);

            BufferedOutputStream writer = new BufferedOutputStream(socket.getOutputStream());
            writer.write(buffer, 0, buffer.length);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
