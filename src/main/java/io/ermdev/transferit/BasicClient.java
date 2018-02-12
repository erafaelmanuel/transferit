package io.ermdev.transferit.client;

import io.ermdev.transferit.exception.TransferitException;

import java.io.*;
import java.net.Socket;

public class BasicClient {

    private volatile Receiver receiver;
    private ClientListener clientListener;

    private Socket connection;

    public BasicClient(Receiver receiver) {
        this.receiver = receiver;
    }

    public void connect() throws TransferitException {
        try {
            connection = new Socket(receiver.getHost(), receiver.getPort());
            receiver.setConnected(true);
        } catch (Exception e) {
            throw new TransferitException("Unable to connect!");
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            receiver.setConnected(false);
        } catch (Exception e) {
            e.printStackTrace();
            receiver.setConnected(false);
        }
    }

    public void keepAlive() {
        new Thread(() -> {
            try {
                boolean isConnected = true;
                while (isConnected) {

                    InputStream is = connection.getInputStream();
                    int n;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((n = is.read()) != -1) {
                        stringBuilder.append((char) n);
                    }

                    if (stringBuilder.toString().equalsIgnoreCase("close")) {
                        is.close();
                        isConnected = false;
                        disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                disconnect();
            }
        }).start();
    }

    public void openTransaction(File file) {
        try {
            Socket socket = new Socket(receiver.getHost(), receiver.getPort());
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
        byte buffer[] = new byte[8192];
        int length = (int) file.length();
        int count = 0;

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeUTF(file.getName());

        if (clientListener != null) {
            int n;
            while ((n = bis.read(buffer)) != -1) {
                dos.write(buffer, 0, n);
                count += n;

                final double percent = (100.0 / length) * (double) count;
                clientListener.onTransferUpdate(percent);
            }
        } else {
            int n;
            while ((n = bis.read(buffer)) != -1) {
                dos.write(buffer, 0, n);
            }
        }

        dos.flush();
        dos.close();
    }

    public void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }


    @FunctionalInterface
    public interface ClientListener {
        void onTransferUpdate(double transfer);
    }
}
