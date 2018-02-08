package io.ermdev.transferit.local.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;

    public Server(int port) {
        this.port = port;
    }

    public void openConnection() {
        try {
            System.out.println("Waiting ...");

            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();

            System.out.println("Connected!");
            System.out.print("Accept a file? (y/n) ");
            while (true) {
                if (new Scanner(System.in).next().equalsIgnoreCase("y")) {
                    receivedFile(true);
                } else {
                    receivedFile(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receivedFile(boolean accept) throws Exception {
        final String FILE_NAME = "sample.mp4";
        if(accept) {
//            File file = new File(FILE_NAME);
//            byte buffer[] = new byte[8192];
//            FileOutputStream fos = new FileOutputStream(file);
//            BufferedOutputStream bos = new BufferedOutputStream(fos);

            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            String fileName = dis.readUTF();
            Files.copy(dis, Paths.get(fileName));
            dis.close();
//            int size;
//            while ((size = bis.read(buffer)) != -1) {
//                bos.write(buffer, 0, size);
//                System.out.println(size);
//            }
//            bos.flush();
            //bos.close();
        }
    }
}
