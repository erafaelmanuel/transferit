package io.ermdev.transferit.local.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class BasicServer {

    private int port;
    private ServerSocket serverSocket;
    private Sender sender;

    public BasicServer(int port) {
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
                        final String address = socket.getInetAddress().getHostAddress();
                        final boolean enabled = true;
                        sender = new Sender(address, enabled);
                    }
                }
                if (sender != null && sender.isEnabled()) {
                    openSession(socket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void openSession(Socket socket) throws Exception {
        if (socket != null && !socket.isClosed()) {
            receivedFile(socket);
            if (!socket.isClosed())
                socket.close();
        }
    }

    private void receivedFile(Socket socket) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        DataInputStream dis = new DataInputStream(bis);

        String fileName = dis.readUTF();
        Files.copy(dis, Paths.get(fileName));
        dis.close();
    }
}
