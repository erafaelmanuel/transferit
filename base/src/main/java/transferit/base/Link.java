package transferit.base;

import java.net.ServerSocket;

public class Link {

    private User user;
    private ServerSocket socket1;
    private ServerSocket socket2;
    private Thread thread1;

    public Link(User user) {
        this.user = user;
        start();
    }

    private void start() {
        thread1 = new Thread(() -> {
            while (true) {

            }
        });
    }
}
