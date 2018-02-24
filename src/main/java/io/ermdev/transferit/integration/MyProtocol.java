package io.ermdev.transferit.integration;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class MyProtocol {

    private Socket socket;

    private String token;

    private Endpoint endpoint;

    private volatile boolean okListen;

    private ProtocolListener protocolListener;

    public MyProtocol(Endpoint endpoint) {
        this.endpoint = endpoint;
        token = (Math.random() * 100000) + "";
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setListener(ProtocolListener protocolListener) {
        this.protocolListener = protocolListener;
    }

    public void dispatch(Status status) throws ClientException {
        try {
            String message = String.format(Locale.ENGLISH, "token=%s&status=%d;", token, status.getCode());
            OutputStream os = socket.getOutputStream();
            os.write(message.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (Exception e) {
            throw new ClientException("Dispatching Failed!");
        }
    }

    private void read(String arg) {
        try {
            final String rToken = arg.split("&")[0].split("token=")[1];
            final String rStatus = arg.split("&")[1].split("status=")[1];
            if (true) {
                switch (Integer.parseInt(rStatus)) {
                    case 100: {
                        token = rToken;
                        if (protocolListener != null) {
                            protocolListener.onCreate();
                        }
                        break;
                    }
                    case 200: {
                        endpoint.setConnected(false);
                        stopListening();
                        System.out.println("Stop");
                        if (protocolListener != null) {
                            protocolListener.onStop();
                        }
                        break;
                    }
                    case 300: {
                        endpoint.setConnected(true);
                        break;
                    }
                    case 400: {
                        endpoint.setConnected(false);
                        break;
                    }
                    default: {
                        throw new TcpException("Unknown status code");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        Thread thread = new Thread(() -> {
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
            } catch (Exception e) {
                stopListening();
            }
        });
        thread.start();
    }

    public void stopListening() {
        okListen = false;
    }
}
