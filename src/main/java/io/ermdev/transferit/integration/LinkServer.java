package io.ermdev.transferit.integration;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LinkServer implements ProtocolListener {

    private ServerSocket server1;

    private ServerSocket server2;

    private MyProtocol protocol;

    private Endpoint endpoint;

    private ServerListener serverListener;

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

    public Socket findSocket() throws ServerException {
        try {
            return server1.accept();
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
                endpoint.setConnected(true);
                channeling();
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

    public void channeling() {
        Thread thread = new Thread(() -> {
            try {
                while (endpoint.isConnected()) {
                    Socket socket = server2.accept();
                    System.out.println("New socket");
                    receivedFile(socket);
                    System.out.println("File send");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
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

    @Override
    public void onCreate() {
        serverListener.onInvite();
    }

    @Override
    public void onStop() {

    }
}
