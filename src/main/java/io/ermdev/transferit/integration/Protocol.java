package io.ermdev.transferit.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Protocol {

    private Socket socket;

    public Protocol() {}

    public void invalidateSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = null;
    }

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
        } else {
            return socket.getInetAddress().getHostAddress().equals(endpoint.getHost());
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        if (isAlive()) {
            invalidateSocket();
        }
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
