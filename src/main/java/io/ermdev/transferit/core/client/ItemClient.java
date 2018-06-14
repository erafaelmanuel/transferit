package io.ermdev.transferit.core.client;

import io.ermdev.transferit.core.Item;
import io.ermdev.transferit.core.client.Client;
import io.ermdev.transferit.core.client.ClientException;
import io.ermdev.transferit.core.client.ClientListener;
import io.ermdev.transferit.desktop.util.TrafficUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

public class ItemClient {

    private Client client;
    private ClientListener listener;

    private volatile boolean okSend;

    public ItemClient(Client client, ClientListener listener) {
        this.client = client;
        this.listener = listener;
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
        if (listener != null) {
            listener.onSendFileStart(item);
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
                    listener.onSendFileUpdate(item, total);
                    long cost = System.currentTimeMillis() - start;
                    if (cost > 0 && System.currentTimeMillis() % 10 == 0) {
                        listener.onTransferSpeed(new TrafficUtil().speed(total / cost));
                    }
                }
                dos.flush();
                dos.close();
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            listener.onSendFileComplete(item);
        }
    }

    public void pause() {
        okSend = false;
    }

    public void play() {
        okSend = true;
    }
}
