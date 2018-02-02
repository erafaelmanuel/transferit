package io.ermdev.transferit.local.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public void findConnection() {
        try {
            final String HOST;
            final String FILE_NAME;

            System.out.print("ENTER HOST : ");
            HOST = new Scanner(System.in).next();

            System.out.print("ENTER FILE NAME : ");
            FILE_NAME = new Scanner(System.in).next();

            File file = new File(FILE_NAME);

            if(file.exists()) {
                Socket socket = new Socket(HOST, 23411);
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
}
