package io.ermdev.transferit;

import io.ermdev.transferit.exception.TransferitException;
import io.ermdev.transferit.fun.ClientListener;

import java.io.*;
import java.net.Socket;

public class TcpClient {

    private ClientListener clientListener;

    private final Endpoint endpoint;

    private Socket connection;

    public TcpClient(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    private Socket createConnection(Endpoint endpoint) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            return new Socket(endpoint.getHost(), endpoint.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void connect() throws TransferitException {
        synchronized (endpoint) {
            try {
                connection = createConnection(endpoint);
                sendPermission();
            } catch (Exception e) {
                throw new TransferitException("Unable to connect!");
            }
        }
    }

    public void disconnect() {
        synchronized (endpoint) {
            try {
                endpoint.setConnected(false);
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPermission() {
        synchronized (endpoint) {
            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        int n;
                        StringBuilder response = new StringBuilder();
                        while ((n = connection.getInputStream().read()) != -1) {
                            response.append((char) n);
                        }
                        switch (response.toString()) {
                            case "close": {
                                disconnect();
                                return;
                            }
                            case "start": {
                                endpoint.setConnected(true);
                                connection = createConnection(endpoint);
                                keepAlive();
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    disconnect();
                }
            });
            thread.start();
        }
    }

    private void keepAlive() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    final StringBuilder stringBuilder = new StringBuilder();
                    int n;
                    while ((n = connection.getInputStream().read()) != -1) {
                        stringBuilder.append((char) n);
                    }
                    if (stringBuilder.toString().equalsIgnoreCase("close")) {
                        endpoint.setConnected(false);
                        connection.close();
                        return;
                    }
                }
            } catch (Exception e) {
                disconnect();
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void openTransaction(File file) {
        try {
            Socket socket = new Socket(endpoint.getHost(), endpoint.getPort());
            send(socket, file);
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            endpoint.setConnected(false);
            e.printStackTrace();
        }
    }

    private void send(Socket socket, File file) throws Exception {
        byte buffer[] = new byte[8192];
        int length = (int) file.length();
        int count = 0;

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeUTF(file.getName());

        if (clientListener != null) {
            int n;
            while ((n = bis.read(buffer)) != -1) {
                dos.write(buffer, 0, n);
                count += n;
                final double percent = (100.0 / length) * (double) count;
                clientListener.onTransfer(percent);
            }
        } else {
            int n;
            while ((n = bis.read(buffer)) != -1) {
                dos.write(buffer, 0, n);
            }
        }
        dos.flush();
        dos.close();
    }

    public void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }
}
