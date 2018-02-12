package io.ermdev.transferit;

import io.ermdev.transferit.exception.TransferitException;
import io.ermdev.transferit.fun.ClientListener;

import java.io.*;
import java.net.Socket;

public class BasicClient {

    private Receiver receiver;

    private ClientListener clientListener;

    private Socket connection;

    public BasicClient(Receiver receiver) {
        this.receiver = receiver;
    }

    public void connect() throws TransferitException {
        try {
            connection = new Socket(receiver.getHost(), receiver.getPort());
            if(isConnectionAccepted()) {
                receiver.setConnected(true);
            } else {
                receiver.setConnected(false);
            }
        } catch (Exception e) {
            throw new TransferitException("Unable to connect!");
        }
    }

    public void disconnect() {
        receiver.setConnected(false);
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isConnectionAccepted() {
        InitialConnection initialConnection = new InitialConnection();
        Thread thread = new Thread(initialConnection);
        thread.start();
        return initialConnection.getValue();
    }

    @Deprecated
    private void keepConnect() {
        Thread thread = new Thread(() -> {
            try {
                boolean isNew = true;
                while (isNew) {
                    final InputStream is = connection.getInputStream();
                    final StringBuilder stringBuilder = new StringBuilder();
                    int n;
                    while ((n = is.read()) != -1) {
                        stringBuilder.append((char) n);
                    }
                    if (stringBuilder.toString().equalsIgnoreCase("close")) {
                        isNew = false;
                    } else if (stringBuilder.toString().equalsIgnoreCase("start")) {
                        receiver.setConnected(true);
                    }
                }
            } catch (Exception e) {
                disconnect();
                e.printStackTrace();
            }
        });
        thread.start();
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
                clientListener.onTransfer(percent);
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

    class InitialConnection implements Runnable {

        private volatile boolean value;

        @Override
        public void run() {
            try {
                while (true) {
                    StringBuilder stringBuilder = new StringBuilder();
                    int n;
                    while ((n = connection.getInputStream().read()) != -1) {
                        stringBuilder.append((char) n);
                    }
                    if (stringBuilder.toString().equalsIgnoreCase("close")) {
                        value = false;
                        break;
                    } else if (stringBuilder.toString().equalsIgnoreCase("start")) {
                        value = true;
                        break;
                    }
                }
            } catch (Exception e) {
                value = false;
            }
        }

        public boolean getValue() {
            return value;
        }
    }
}
