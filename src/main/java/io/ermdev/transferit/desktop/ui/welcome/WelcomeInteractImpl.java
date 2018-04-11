package io.ermdev.transferit.desktop.ui.welcome;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

public class WelcomeInteractImpl implements WelcomeInteract {

    private int port = 0;

    WelcomeInteractImpl() {
        try {
            final ClassLoader classLoader = getClass().getClassLoader();
            final Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream("config/application.properties"));
            port = Integer.parseInt(properties.getProperty("app.port", "0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectSend(Stage stage, WelcomeListener listener) {
        if (true) {
            listener.onSend(stage);
        } else {
            listener.onUnableToSend();
        }
    }

    @Override
    public void selectReceive(Stage stage, WelcomeListener listener) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            serverSocket.close();
            listener.onReceive(stage);
        } catch (Exception e) {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            listener.onUnableToReceive();
        }
    }

    @Override
    public void selectOption(double x, double y, WelcomeListener listener) {
        listener.onOption(x, y);
    }
}
