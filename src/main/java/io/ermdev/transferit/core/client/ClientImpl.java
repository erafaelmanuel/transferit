package io.ermdev.transferit.core.client;

import io.ermdev.transferit.core.protocol.State;
import io.ermdev.transferit.core.protocol.Protocol;
import io.ermdev.transferit.core.protocol.Status;

import java.io.File;
import java.net.Socket;

public class ClientImpl implements Client {

    private Protocol protocol;

    public ClientImpl(final State state) {
        protocol = new Protocol(state);
    }

    @Override
    public void connect() throws ClientException {
        try {
            final Socket socket = new Socket(protocol.getState().getHost(), protocol.getState().getPort());
            protocol.setSocket(socket);
            protocol.dispatch(Status.CREATE);
            protocol.listen();
        } catch (Exception e) {
            throw new ClientException("Unable to connect!");
        }
    }

    @Override
    public void disconnect() {
        try {
            protocol.getState().setConnected(false);
            protocol.dispatch(Status.STOP);
            protocol.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public State getState() {
        return protocol.getState();
    }

    @Override
    public void sendFile(File file) {
        try {
            protocol.dispatch(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
