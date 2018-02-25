package io.ermdev.transferit.integration;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class ItemServer {

    private List<Item> items;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void receiveFile(InputStream inputStream) {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
            String fileName = dis.readUTF();
            Optional<Item> o = items.parallelStream().filter(item -> item.getName().equals(fileName)).findFirst();
            File file = new File(fileName);
            byte buffer[] = new byte[10240];
            if (o.isPresent() && (!file.exists() || file.delete())) {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                Item item = o.get();
                item.notifyBefore();
                int total = 0;
                int read;
                while ((read = dis.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                    total += read;
                    item.setProgress(total);
                }
                item.notifyAfter();
                dis.close();
                items.remove(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
