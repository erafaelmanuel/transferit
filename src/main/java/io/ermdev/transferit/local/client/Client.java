package io.ermdev.transferit.local.client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class Client {

    private Socket socket;

    public Client(final String host, final int port) throws IOException {
        socket = new Socket(host, port);
    }

    public void send(File file) {
        try {
            if (file.exists()) {
//                byte buffer[] = new byte[(int) file.length()];
//                FileInputStream fis = new FileInputStream(file);
//                BufferedInputStream bis = new BufferedInputStream(fis);
//                bis.read(buffer, 0, buffer.length);
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());


                DataOutputStream dos = new DataOutputStream(bos);
                dos.writeUTF(file.getName());
                Files.copy(file.toPath(), dos);
                dos.close();

//                bos.write(buffer, 0, buffer.length);
//                bos.flush();
//                bos.close();
            } else {
                System.out.println("Invalid file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public Socket getSocket() {
        return socket;
    }
}
