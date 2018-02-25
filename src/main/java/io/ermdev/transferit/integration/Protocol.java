package io.ermdev.transferit.integration;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Protocol {

    private Endpoint endpoint;

    private Socket socket;

    private Thread postman;

    private volatile boolean okListen;

    private ProtocolListener protocolListener;

    Protocol(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setListener(ProtocolListener protocolListener) {
        this.protocolListener = protocolListener;
    }

    public void dispatch(Status status) throws ClientException {
        try {
            String message = String.format(Locale.ENGLISH, "token=12345&status=%d;", status.getCode());
            OutputStream os = socket.getOutputStream();
            os.write(message.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (Exception e) {
            throw new ClientException("Dispatching Failed!");
        }
    }

    private void read(String arg) {
        try {
            final int status = Integer.parseInt(arg.split("&")[1].split("status=")[1]);
            if (status == 100) {
                if (protocolListener != null) {
                    protocolListener.onCreate();
                }
            } else if (status == 200) {
                endpoint.setConnected(false);
                stopListening();
            } else if (status == 300) {
                endpoint.setConnected(true);
            } else if (status == 400) {
                endpoint.setConnected(false);
                stopListening();
            } else {
                throw new TcpException("Unknown status code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        postman = new Thread(() -> {
            try {
                okListen = true;
                while (okListen) {
                    String message = "";
                    int n;
                    while ((n = socket.getInputStream().read()) != -1) {
                        if ((char) n == ';') {
                            read(message);
                            message = "";
                        } else {
                            message = message.concat(String.valueOf((char) n));
                        }
                    }
                }
                postman = null;
            } catch (Exception e) {
                endpoint.setConnected(false);
                stopListening();
            }
        });
        postman.start();
    }

    public void stopListening() {
        okListen = false;
    }
}
