package io.ermdev.transferit.integration;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LinkServer implements Server, ProtocolListener {

    private ServerSocket server1;

    private ServerSocket server2;

    private MyProtocol protocol;

    private Endpoint endpoint;

    private ServerListener serverListener;

    private Thread acceptor;

    private Thread opener;

    public LinkServer(Endpoint endpoint) {
        try {
            this.endpoint = endpoint;
            server1 = new ServerSocket(endpoint.getPort());
            server2 = new ServerSocket(endpoint.getPort() + 1);
            protocol = new MyProtocol(endpoint);
            protocol.setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setServerListener(ServerListener serverListener) {
        this.serverListener = serverListener;
    }

    @Override
    public void open() {
        opener = new Thread(() -> {
            while (!endpoint.isConnected()) {
                try {
                    Socket socket = server1.accept();
                    endpoint.setHost(socket.getInetAddress().getHostAddress());
                    protocol.setSocket(socket);
                    protocol.listen();
                } catch (Exception e) {
                    protocol.stopListening();
                }
            }
            opener = null;
        });
        opener.start();
    }

    @Override
    public void accept() {
        acceptor = new Thread(() -> {
            try {
                protocol.dispatch(Status.ACCEPT);
                endpoint.setConnected(true);
                while (endpoint.isConnected()) {
                    Socket socket = server2.accept();
                    receiveFile(socket.getInputStream());
                }
            } catch (Exception e) {
                protocol.stopListening();
            }
            acceptor = null;
        });
        acceptor.start();
    }

    @Override
    public void stop() {
        try {
            protocol.stopListening();
            protocol.dispatch(Status.STOP);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveFile(InputStream inputStream) {
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            DataInputStream dis = new DataInputStream(bis);
            String fileName = dis.readUTF();
            File file = new File(fileName);
            if (!file.exists() || file.delete()) {
                Files.copy(dis, Paths.get(fileName));
            }
            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        serverListener.onInvite();
    }
}
