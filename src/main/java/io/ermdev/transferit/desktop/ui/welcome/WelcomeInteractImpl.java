package io.ermdev.transferit.desktop.ui.welcome;

import io.ermdev.transferit.desktop.util.MasterConfig;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;

public class WelcomeInteractImpl implements WelcomeInteract {

    final private MasterConfig masterConfig = new MasterConfig();

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
            serverSocket = new ServerSocket(masterConfig.getPortOrDefault());
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
