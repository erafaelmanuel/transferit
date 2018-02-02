package io.ermdev.transferit.local.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
            receivedFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receivedFile() throws Exception {
        final String FILE_NAME;
        System.out.print("ENTER FILE NAME : ");
        FILE_NAME = new Scanner(System.in).next();

        byte buffer[] = new byte[4303444];
        File file = new File(FILE_NAME);

        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());

        int n;
        while((n = bis.read(buffer, 0, buffer.length)) != -1) {
            bos.write(buffer, 0, n);
        }
        bos.flush();
        bos.close();
    }
}
