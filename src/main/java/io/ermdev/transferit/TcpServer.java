package io.ermdev.transferit;

import io.ermdev.transferit.fun.ServerListener;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TcpServer {

    private ServerSocket serverSocket;

    private Socket connection;

    private final Endpoint endpoint;

    private ServerListener serverListener;

    public TcpServer(Endpoint endpoint) {
        this.endpoint = endpoint;
        try {
            serverSocket = new ServerSocket(endpoint.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findConnection() {
        synchronized (endpoint) {
            Thread thread = new Thread(() -> {
                try {
                    connection = serverSocket.accept();
                    endpoint.setHost(connection.getInetAddress().getHostAddress());
                    if (serverListener != null) {
                        serverListener.onInvite();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }

    public void accept() {
        synchronized (endpoint) {
            try {
                OutputStream os = connection.getOutputStream();
                os.write("start".getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                endpoint.setConnected(true);
                keepAlive();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reject() {
        synchronized (endpoint) {
            try {
                OutputStream os = connection.getOutputStream();
                os.write("close".getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                endpoint.setConnected(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        synchronized (endpoint) {
            try {
                if(connection != null && !connection.isClosed()) {
                    OutputStream os = connection.getOutputStream();
                    os.write("close".getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                }
                endpoint.setConnected(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void keepAlive() {
        synchronized (endpoint) {
            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        connection = serverSocket.accept();
                        if (connection.getInetAddress().getHostAddress().equals(endpoint.getHost())) {
                            while (true) {
                                final StringBuilder stringBuilder = new StringBuilder();
                                int n;
                                while ((n = connection.getInputStream().read()) != -1) {
                                    stringBuilder.append((char) n);
                                }
                                if (stringBuilder.toString().equalsIgnoreCase("close")) {
                                    stop();
                                    return;
                                }
                            }
                        } else {
                            reject();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
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

    public void setServerListener(ServerListener serverListener) {
        this.serverListener = serverListener;
    }
}
