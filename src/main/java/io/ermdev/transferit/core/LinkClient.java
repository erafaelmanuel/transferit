package io.ermdev.transferit.core;

import java.io.File;
import java.net.Socket;

public class LinkClient implements Client {

    private Protocol protocol;

    private Endpoint endpoint;

    public LinkClient(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void connect() throws ClientException {
        try {
            protocol = new Protocol(newSocket(), endpoint);
            protocol.dispatch(Status.CREATE);
            protocol.listen();
        } catch (Exception e) {
            throw new ClientException("Unable to connect!");
        }
    }

    @Override
    public void disconnect() {
        try {
            endpoint.setConnected(false);
            protocol.dispatch(Status.STOP);
            protocol.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
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
        try {
            protocol.dispatch(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
