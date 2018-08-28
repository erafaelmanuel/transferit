package transferit.base;

public class Signature {

    private String host;

    private int port;

    private Signature(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static Signature of(String host, int port) {
        return new Signature(host, port);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
