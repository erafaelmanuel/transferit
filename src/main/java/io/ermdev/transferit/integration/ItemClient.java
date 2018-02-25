package io.ermdev.transferit.integration;

import io.ermdev.transferit.desktop.util.TrafficUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

public class ItemClient implements InteractiveClient {

    private ClientListener clientListener;

    private Client client;

    private volatile boolean okSend;

    public ItemClient(Client client, ClientListener listener) {
        this.client = client;
        this.setListener(listener);
    }

    public Client getClient() {
        return client;
    }

    public Socket newSocket() throws ClientException {
        try {
            return new Socket(client.getEndpoint().getHost(), client.getEndpoint().getPort() + 1);
        } catch (Exception e) {
            throw new ClientException("Failed to make a socket!");
        }
    }

    public void sendItem(final Item item) {
        if (getListener() != null) {
            getListener().onSendFileStart(item);
            okSend = true;
            try {
                int total = 0;
                int read;
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(item.getFile()));
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(newSocket().getOutputStream()));
                dos.writeUTF(item.getName());

                byte buffer[] = new byte[10240];
                long start = System.currentTimeMillis();
                while ((read = bis.read(buffer)) != -1) {
                    while (!okSend) {
                    }
                    dos.write(buffer, 0, read);
                    total += read;
                    getListener().onSendFileUpdate(item, total);
                    long cost = System.currentTimeMillis() - start;
                    if (cost > 0 && System.currentTimeMillis() % 10 == 0) {
                        getListener().onTransferSpeed(new TrafficUtil().speed(total / cost));
                    }
                }
                dos.flush();
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getListener().onSendFileComplete(item);
        }
    }

    public void pause() {
        okSend = false;
    }

    public void play() {
        okSend = true;
    }

    @Override
    public ClientListener getListener() {
        return clientListener;
    }

    @Override
    public void setListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }
}
