package io.ermdev.transferit.integration;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Protocol {

    private Socket socket;

    public Protocol() {}

    public boolean isAlive() {
        if (socket == null) {
            return false;
        }
        if (socket.isClosed()) {
            return false;
        }
        if (!socket.isConnected()) {
            return false;
        }
        return true;
    }

    public boolean isAlive(Endpoint endpoint) {
        if (!isAlive()) {
            return false;
        }
        if (!socket.getInetAddress().getHostAddress().equals(endpoint.getHost())) {
            return false;
        }
        return true;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        try {
            if (isAlive()) {
                this.socket.close();
            }
        } catch (Exception e) {}
        this.socket = socket;
    }

    public InputStream getInputStream() throws Exception {
        if (isAlive()) {
            return socket.getInputStream();
        } else {
            throw new RuntimeException("Socket is not available");
        }
    }

    public OutputStream getOutputStream() throws Exception {
        if (isAlive()) {
            return socket.getOutputStream();
        } else {
            throw new RuntimeException("Socket is not available");
        }
    }
}
