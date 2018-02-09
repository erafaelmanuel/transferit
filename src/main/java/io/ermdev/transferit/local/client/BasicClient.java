package io.ermdev.transferit.local.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.nio.file.Files;

public class BasicClient {

    private Receiver receiver;

    public BasicClient(Receiver receiver) {
        this.receiver = receiver;
    }

    public void openTransaction(File file) {
        try {
            Socket socket = new Socket(receiver.getHost(), receiver.getPort());
            receiver.setConnected(true);
            send(socket, file);
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            receiver.setConnected(false);
            e.printStackTrace();
        }
    }

    private void send(Socket socket, File file) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeUTF(file.getName());
        Files.copy(file.toPath(), dos);
        dos.close();
    }
}
