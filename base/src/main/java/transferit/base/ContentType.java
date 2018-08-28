package transferit.base;

public enum ContentType {

    PLAIN_TEXT(1), JPEG(2), PNG(3), MP3(4), MP4(5);

    private int key;

    ContentType(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
