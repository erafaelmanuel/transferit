package io.ermdev.transferit.integration;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class ItemServer {

    private List<Item> items;
    private String path = "files";

    public void setPath(String path) {
        this.path = path;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void receiveFile(InputStream inputStream) {
        try {
            final DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
            final String fileName = dis.readUTF();
            final Optional<Item> o = items.parallelStream().filter(item -> item.getName().equals(fileName)).findFirst();
            final File directory = new File(path);
            if (!(directory.exists() || directory.mkdirs())) {
                throw new RuntimeException("File folder is missing");
            }
            File file = new File((path != null ? path : "files") + ("/" + fileName));
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
                bos.flush();
                bos.close();
                dis.close();
                items.remove(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
