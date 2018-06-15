package io.ermdev.transferit.core.client;

import io.ermdev.transferit.core.protocol.Item;
import io.ermdev.transferit.desktop.util.TrafficUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

public class ClientItem {

    private Client client;

    private ClientListener listener;

    private volatile boolean okSend;

    public ClientItem(final Client client, final ClientListener listener) {
        this.client = client;
        this.listener = listener;
    }

    public Client getClient() {
        return client;
    }

    public void sendItem(final Item item) {
        if (listener != null) {
            listener.onSendFileStart(item);
            okSend = true;
            try {
                final Socket socket = new Socket(client.getState().getHost(), client.getState().getPort() + 1);
                final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(item.getFile()));
                final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                int total = 0;
                int read;

                final byte buffer[] = new byte[10240];
                final long start = System.currentTimeMillis();

                dos.writeUTF(item.getName());
                while ((read = bis.read(buffer)) != -1) {
                    while (!okSend) {
                    }
                    final long cost = System.currentTimeMillis() - start;

                    dos.write(buffer, 0, read);
                    total += read;

                    listener.onSendFileUpdate(item, total);
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
