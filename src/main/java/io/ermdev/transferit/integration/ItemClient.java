package io.ermdev.transferit.integration;

import io.ermdev.transferit.desktop.util.TrafficUtil;
import io.ermdev.transferit.integration.v2.ClientListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;

public class ItemClient implements InteractiveClient {

    private ClientListener listener;
    private Client client;

    public ItemClient(Client client, ClientListener listener) {
        this.client = client;
        this.setListener(listener);
    }

    public void sendItem(final Item item) {
        if (getListener() != null) {
            getListener().onSendFileStart(item);
            try {
                int total = 0;
                int read;
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(item.getFile()));
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(client.newSocket().getOutputStream()));
                dos.writeUTF(item.getName());

                byte buffer[] = new byte[10240];
                long start = System.currentTimeMillis();
                while ((read = bis.read(buffer)) != -1) {
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

    @Override
    public ClientListener getListener() {
        return listener;
    }

    @Override
    public void setListener(ClientListener listener) {
        this.listener = listener;
    }
}
