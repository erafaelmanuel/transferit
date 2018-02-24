package io.ermdev.transferit.integration;

import java.net.ServerSocket;
import java.net.Socket;

public class LinkServer implements ProtocolListener {

    private ServerSocket serverSocket;

    private MyProtocol protocol;

    private Endpoint endpoint;

    private ServerListener serverListener;

    public LinkServer(Endpoint endpoint) {
        try {
            this.endpoint = endpoint;
            this.serverSocket = new ServerSocket(endpoint.getPort());
            protocol = new MyProtocol(endpoint);
            protocol.setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setServerListener(ServerListener serverListener) {
        this.serverListener = serverListener;
    }

    public Socket findSocket() throws ServerException {
        try {
            return serverSocket.accept();
        } catch (Exception e) {
            throw new ServerException("No socket found");
        }
    }

    public void open() {
        Thread thread = new Thread(() -> {
            while (!endpoint.isConnected()) {
                try {
                    Socket socket = findSocket();
                    endpoint.setHost(socket.getInetAddress().getHostAddress());
                    protocol.setSocket(socket);
                    protocol.listen();
                } catch (Exception e) {
                    e.printStackTrace();
                    protocol.stopListening();
                }
            }
        });
        thread.start();
    }

    public void accept() {
        Thread thread = new Thread(() -> {
            try {
                protocol.dispatch(Status.ACCEPT);
            } catch (Exception e) {
                e.printStackTrace();
                protocol.stopListening();
            }
        });
        thread.start();
    }

    public void close() {
        try {
            protocol.dispatch(Status.STOP);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        protocol.stopListening();
    }

    @Override
    public void onCreate() {
        serverListener.onInvite();
    }

    @Override
    public void onStop() {

    }
}
