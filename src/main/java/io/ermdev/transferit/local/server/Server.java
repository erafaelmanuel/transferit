package io.ermdev.transferit.local.server;

import io.ermdev.transferit.local.Signature;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private Signature sender;

    public Server(int port) {
        this.port = port;
    }

    public void channeling() throws Exception {
        serverSocket = new ServerSocket(port);
        while (true) {
            try {
                System.out.println("Waiting ...");
                Socket socket = serverSocket.accept();

                if (sender == null || !sender.isEnabled()) {
                    System.out.println("Someone want to connect!");
                    System.out.println("Accept? (y/n) ");

                    if (new Scanner(System.in).next().equalsIgnoreCase("y")) {
                        System.out.println(socket.getInetAddress().getCanonicalHostName());
                        System.out.println(socket.getInetAddress().getHostAddress());
                        System.out.println(socket.getInetAddress().getHostName());
                        sender = new Signature(socket.getInetAddress().getHostAddress(), true);
                    }
                }
                if (sender != null && sender.isEnabled()) {
                    String fileName = ((int) (Math.random() * 100)) + ".mp3";
                    File file = new File(fileName);
                    openSession(socket, file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void openSession(Socket socket, File file) throws Exception {
        if (socket != null && !socket.isClosed()) {
            receivedFile(socket, file);
            if (!socket.isClosed())
                socket.close();
        }
    }

    public void receivedFile(Socket socket, File file) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte buffer[] = new byte[8192];
        int length;

        while ((length = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, length);
            System.out.println(length);
        }
        bos.flush();
        bos.close();
    }
}
