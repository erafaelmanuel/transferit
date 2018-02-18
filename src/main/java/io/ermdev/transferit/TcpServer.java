package io.ermdev.transferit;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TcpServer {

    private int port = 23411;
    private ServerSocket serverSocket;
    private Socket connection;
    private Endpoint endpoint;

    public TcpServer(int port) {
        try {
            this.port = port;
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupConnection(Endpoint endpoint) {
        this.endpoint = endpoint;
        try {
            boolean isNotConnected = true;
            while (isNotConnected) {
                System.out.println("Finding a connection...");
                connection = serverSocket.accept();
                System.out.println("Someone want to connect!");
                System.out.println("Accept? (y/n) ");
                if (new Scanner(System.in).next().equalsIgnoreCase("y")) {
                    endpoint.setHost(connection.getInetAddress().getHostAddress());
                    endpoint.setConnected(true);
                    isNotConnected = false;
                    OutputStream os = connection.getOutputStream();
                    os.write("start".getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                } else {
                    OutputStream os = connection.getOutputStream();
                    os.write("close".getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                    isNotConnected = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keepAlive() {

    }

    public void channeling() throws Exception {
        while (true) {
            try {
                System.out.println("Channeling ...");
                Socket socket = serverSocket.accept();
                if (endpoint != null && endpoint.isConnected()) {
                    openSession(socket);
                } else {
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void openSession(Socket socket) throws Exception {
        if (socket != null && !socket.isClosed()) {
            receivedFile(socket);
            if (!socket.isClosed()) {
                socket.close();
            }
        }
    }

    private void receivedFile(Socket socket) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        DataInputStream dis = new DataInputStream(bis);

        String fileName = dis.readUTF();
        File file = new File(fileName);
        if (!file.exists() || file.delete()) {
            Files.copy(dis, Paths.get(fileName));
        }
        dis.close();
    }
}
