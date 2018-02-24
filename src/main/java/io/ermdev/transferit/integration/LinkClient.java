package io.ermdev.transferit.integration;

import java.io.File;
import java.net.Socket;

public class LinkClient implements Client {

    private MyProtocol protocol;
    private Endpoint endpoint;

    public LinkClient(Endpoint endpoint) {
        this.endpoint = endpoint;
        protocol = new MyProtocol(endpoint);
    }

    @Override
    public void connect() throws ClientException {
        try {
            protocol.setSocket(newSocket());
            protocol.dispatch(Status.CREATE);
            protocol.listen();
        } catch (Exception e) {
            throw new ClientException("Unable to connect!");
        }
    }

    @Override
    public void disconnect() {
        try {
            protocol.dispatch(Status.STOP);
            protocol.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
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

    }
}
