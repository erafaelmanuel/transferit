package io.ermdev.transferit.integration;

import io.ermdev.transferit.desktop.util.TrafficUtil;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpClient implements Client {

    private final Endpoint endpoint;

    private ClientListener listener;

    private Protocol protocol = new Protocol();

    public TcpClient(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    private Socket createConnection(Endpoint endpoint) throws TcpException {
        try {
            return new Socket(endpoint.getHost(), endpoint.getPort());
        } catch (Exception e) {
            throw new TcpException("Failed to make a socket!");
        }
    }

    @Override
    public void connect() throws TcpException {
        synchronized (endpoint) {
            try {
                sendRequestConnection(createConnection(endpoint));
            } catch (Exception e) {
                throw new TcpException("Unable to connect!");
            }
        }
    }

    @Override
    public void disconnect() {
        synchronized (endpoint) {
            try {
                if (protocol.isAlive()) {
                    sendMessage("close", protocol.getOutputStream());
                }
            } catch (Exception e) {
                protocol.invalidateSocket();
            }
            endpoint.setConnected(false);
        }
    }

    private void observeConnection() {
        Thread thread = new Thread(() -> {
            try {
                protocol.setSocket(createConnection(endpoint));
                while (true) {
                    if (receiveMessage(protocol.getInputStream()).equals("close")) {
                        protocol.invalidateSocket();
                        endpoint.setConnected(false);
                        return;
                    }
                }
            } catch (Exception e) {
                protocol.invalidateSocket();
                endpoint.setConnected(false);
            }
        });
        thread.start();
    }

    private String receiveMessage(InputStream is) throws TcpException {
        final StringBuilder response = new StringBuilder();
        try {
            int n;
            while ((n = is.read()) != -1) {
                response.append((char) n);
            }
            return response.toString();
        } catch (Exception e) {
            throw new TcpException("Receiving message failed");
        }
    }

    private void sendRequestConnection(final Socket connection) {
        synchronized (endpoint) {
            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        switch (receiveMessage(connection.getInputStream())) {
                            case "close": {
                                connection.close();
                                endpoint.setConnected(false);
                                return;
                            }
                            case "start": {
                                connection.close();
                                endpoint.setConnected(true);
                                observeConnection();
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    endpoint.setConnected(false);
                }
            });
            thread.start();
        }
    }

    public void sendFile(File file) {
        synchronized (endpoint) {
            try {
                startLinking(createConnection(endpoint), file);
            } catch (Exception e) {
                endpoint.setConnected(false);
            }
        }
    }

    private void sendMessage(String message, OutputStream os) throws TcpException {
        try {
            os.write(message.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        } catch (Exception e) {
            throw new TcpException("Sending message failed");
        }
    }

    @Deprecated
    private void startLinking(Socket socket, File file) throws Exception {
        int total = 0;
        int read;

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeUTF(file.getName());

        if (listener != null) {
            byte buffer[] = new byte[10240];
            long start = System.currentTimeMillis();
            listener.onStart();
            while ((read = bis.read(buffer)) != -1) {
                dos.write(buffer, 0, read);
                total += read;
                long cost = System.currentTimeMillis() - start;
                if (cost > 0 && System.currentTimeMillis() % 10 == 0) {
                    speed = new TrafficUtil().speed(total / cost);
                }
                listener.onUpdate(total);
            }
            listener.onComplete(file.length());
        } else {
            byte buffer[] = new byte[8192];
            while ((read = bis.read(buffer)) != -1) {
                dos.write(buffer, 0, read);
            }
        }
        dos.flush();
        dos.close();

        if (!socket.isClosed()) {
            socket.close();
        }
    }

    @Override
    public void setListener(ClientListener listener) {
        this.listener = listener;
    }

    String speed = "0 byte/s";

    public String getSpeed() {
        return speed;
    }
}
