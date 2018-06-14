package io.ermdev.transferit.core.protocol;

import java.io.File;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Protocol {

    private State state;

    private Socket socket;

    private Thread postman;

    private volatile boolean okListen;

    private ProtocolListener protocolListener;

    public Protocol(State state) {
        this.state = state;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setListener(ProtocolListener protocolListener) {
        this.protocolListener = protocolListener;
    }

    public void dispatch(Status status) {
        if (socket != null) {
            try {
                final String message = "content-type=action"
                        .concat("?")
                        .concat("status=")
                        .concat(status.toString())
                        .concat(";");
                final OutputStream os = socket.getOutputStream();
                os.write(message.getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (Exception e) {
            }
        }
    }

    public void dispatch(File file) {
        if (socket != null) {
            try {
                final String message = "content-type=file"
                        .concat("?")
                        .concat("file=")
                        .concat(file.getName())
                        .concat(":")
                        .concat(String.valueOf(file.length()))
                        .concat(";");
                final OutputStream os = socket.getOutputStream();
                os.write(message.getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (Exception e) {
            }
        }
    }

    public void listen() {
        if (socket != null) {
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
                    state.setConnected(false);
                    stopListening();
                }
            });
            postman.start();
        }
    }

    private void read(String message) {
        try {
            final String content = message.split("\\?")[0].split("content-type=")[1];
            if (content.equals("action")) {
                final int status = Integer.parseInt(message.split("\\?")[1].split("status=")[1]);
                if (status == 100) {
                    if (protocolListener != null) {
                        protocolListener.onCreate();
                    }
                } else if (status == 101) {
                    state.setConnected(true);
                } else if (status == 103) {
                    if (protocolListener != null) {
                        protocolListener.onScan();
                    }
                } else if (status == 200) {
                    state.setConnected(false);
                    stopListening();
                } else if (status == 201) {
                    state.setConnected(false);
                    stopListening();
                } else {
                    throw new ProtocolException("Unknown status code");
                }
            } else if (content.equals("file")) {
                final String file = message.split("\\?")[1].split("file=")[1];
                final String name = file.split(":")[0];
                final long size = Long.parseLong(file.split(":")[1]);
                protocolListener.onFile(name, size);
            }
        } catch (Exception e) {
        }
    }

    public void stopListening() {
        okListen = false;
    }

    public boolean isBusy() {
        return okListen;
    }

    public static void reject(Socket socket) {
        if (socket != null) {
            try {
                final Status status = Status.REJECT;
                final String message = "content-type=action"
                        .concat("?")
                        .concat("status=")
                        .concat(status.toString())
                        .concat(";");
                final OutputStream os = socket.getOutputStream();
                os.write(message.getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (Exception e) {
            }
        }
    }

    public State getState() {
        return state;
    }
}
