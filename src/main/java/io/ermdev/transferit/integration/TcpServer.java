package io.ermdev.transferit.integration;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TcpServer implements Server {

    private ServerSocket serverSocket;

    private Socket connection;

    private final Endpoint endpoint;

    private ServerListener serverListener;

    private volatile boolean ON_FIND_USER = false;

    public TcpServer(Endpoint endpoint) {
        this.endpoint = endpoint;
        try {
            serverSocket = new ServerSocket(endpoint.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addListener(ServerListener serverListener) {
        this.serverListener = serverListener;
    }

    @Override
    public void findConnection() {
        System.out.println("Call me maybe");
        synchronized (endpoint) {
            if (!ON_FIND_USER) {
                ON_FIND_USER = true;
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
                    ON_FIND_USER = false;
                });
                thread.start();
            }
        }
    }

    @Override
    public void accept() {
        synchronized (endpoint) {
            try {
                OutputStream os = connection.getOutputStream();
                os.write("start".getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            endpoint.setConnected(true);
            keepAlive();
            channeling();
        }
    }

    @Override
    public void reject() {
        synchronized (endpoint) {
            try {
                if (connection != null && !connection.isClosed()) {
                    OutputStream os = connection.getOutputStream();
                    os.write("close".getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            endpoint.setConnected(false);
        }
    }

    @Override
    public void stop() {
        synchronized (endpoint) {
            try {
                if (connection != null && !connection.isClosed()) {
                    OutputStream os = connection.getOutputStream();
                    os.write("close".getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                }
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            endpoint.setConnected(false);
        }
    }

    @Override
    public void keepAlive() {
        synchronized (endpoint) {
            while (endpoint.isConnected()) {
                try {
                    connection = serverSocket.accept();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (connection.getInetAddress().getHostAddress().equals(endpoint.getHost())) {
                    Thread thread = new Thread(() -> {
                        try {
                            while (endpoint.isConnected()) {
                                final StringBuilder stringBuilder = new StringBuilder();
                                int n;
                                while ((n = connection.getInputStream().read()) != -1) {
                                    stringBuilder.append((char) n);
                                }
                                if (stringBuilder.toString().equalsIgnoreCase("close")) {
                                    stop();
                                    if (serverListener != null) {
                                        serverListener.onStop();
                                    }
                                }
                            }
                        } catch (Exception e) {}
                    });
                    thread.start();
                    return;
                } else {
                    reject();
                }
            }
        }
    }

    public void channeling() {
        synchronized (endpoint) {
            Thread thread = new Thread(() -> {
                while (endpoint.isConnected()) {
                    final Socket socket;
                    try {
                        System.out.println("Channeling ...");
                        socket = serverSocket.accept();
                        if (endpoint.isConnected()) {
                            openSession(socket);
                        } else {
                            socket.close();
                        }
                    } catch (Exception e) {
                    }
                }
            });
            thread.start();
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
