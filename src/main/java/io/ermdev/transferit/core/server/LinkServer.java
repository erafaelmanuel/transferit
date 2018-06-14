package io.ermdev.transferit.core.server;

import io.ermdev.transferit.core.protocol.State;
import io.ermdev.transferit.core.protocol.Status;
import io.ermdev.transferit.core.protocol.Protocol;
import io.ermdev.transferit.core.protocol.ProtocolListener;

import java.net.ServerSocket;
import java.net.Socket;

public class LinkServer implements Server, ProtocolListener {

    private ServerSocket server1;

    private ServerSocket server2;

    private Protocol protocol;

    private State state;

    private ServerListener serverListener;

    private Thread acceptor;

    private Thread rejecter;

    private Thread opener;

    public LinkServer(State state) {
        try {
            this.state = state;
            server1 = new ServerSocket(state.getPort());
            server2 = new ServerSocket(state.getPort() + 1);
            protocol = new Protocol(state);
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
            while (true) {
                try {
                    Socket socket = server1.accept();
                    if (!state.isConnected() && !protocol.isBusy()) {
                        state.setHost(socket.getInetAddress().getHostAddress());
                        protocol.setSocket(socket);
                        protocol.listen();
                    } else {
                        Protocol.reject(socket);
                    }
                } catch (Exception e) {
                    protocol.stopListening();
                    return;
                }
            }
        });
        opener.start();
    }

    @Override
    public void stop() {
        try {
            protocol.stopListening();
            protocol.dispatch(Status.STOP);
            server1.close();
            server2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept() {
        acceptor = new Thread(() -> {
            try {
                protocol.dispatch(Status.ACCEPT);
                state.setConnected(true);
                while (state.isConnected()) {
                    Socket socket = server2.accept();
                    serverListener.onReceiveFile(socket.getInputStream());
                }
            } catch (Exception e) {
                protocol.stopListening();
            }
            acceptor = null;
        });
        acceptor.start();
    }

    @Override
    public void reject() {
        rejecter = new Thread(() -> {
            try {
                protocol.stopListening();
                protocol.dispatch(Status.REJECT);
            } catch (Exception e) {
                protocol.stopListening();
            }
            rejecter = null;
        });
        rejecter.start();
    }

    @Override
    public void onCreate() {
        serverListener.onInvite();
    }

    @Override
    public void onFile(String name, long size) {
        serverListener.onNewFile(name, size);
    }

    @Override
    public void onScan() {
        protocol.dispatch(Status.INFO);
    }
}
