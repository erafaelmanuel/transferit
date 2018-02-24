package io.ermdev.transferit.integration;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpClient implements Client {

    private final Endpoint endpoint;

    private Protocol protocol = new Protocol();

    public TcpClient(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void connect() throws ClientException {
        synchronized (endpoint) {
            try {
                sendRequestConnection(newSocket());
            } catch (Exception e) {
                throw new ClientException("Unable to connect!");
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

    @Override
    public Socket newSocket() throws ClientException {
        try {
            return new Socket(endpoint.getHost(), endpoint.getPort());
        } catch (Exception e) {
            throw new ClientException("Failed to make a socket!");
        }
    }

    @Override
    public void sendFile(File file) {
        synchronized (endpoint) {
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);

                BufferedOutputStream bos = new BufferedOutputStream(newSocket().getOutputStream());
                DataOutputStream dos = new DataOutputStream(bos);
                dos.writeUTF(file.getName());

                byte buffer[] = new byte[8192];
                int read;
                while ((read = bis.read(buffer)) != -1) {
                    dos.write(buffer, 0, read);
                }
                dos.flush();
                dos.close();
            } catch (Exception e) {
                endpoint.setConnected(false);
            }
        }
    }

    private void observeConnection() {
        Thread thread = new Thread(() -> {
            try {
                protocol.setSocket(newSocket());
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

    private String receiveMessage(InputStream is) throws ClientException {
        final StringBuilder response = new StringBuilder();
        try {
            int n;
            while ((n = is.read()) != -1) {
                response.append((char) n);
            }
            return response.toString();
        } catch (Exception e) {
            throw new ClientException("Receiving message failed");
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

    private void sendMessage(String message, OutputStream os) throws ClientException {
        try {
            os.write(message.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        } catch (Exception e) {
            throw new ClientException("Sending message failed");
        }
    }
}
