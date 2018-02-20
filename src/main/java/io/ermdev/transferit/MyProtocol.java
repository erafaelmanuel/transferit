package io.ermdev.transferit;

import java.net.Socket;

public class MyProtocol {

    private Socket socket;

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
        } catch (Exception e) {
            this.socket = null;
        }
        this.socket = socket;
    }
}
