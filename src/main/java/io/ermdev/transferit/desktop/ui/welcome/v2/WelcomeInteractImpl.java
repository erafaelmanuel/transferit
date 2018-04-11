package io.ermdev.transferit.desktop.ui.welcome.v2;

public class WelcomeInteractImpl implements WelcomeInteract {

    @Override
    public void selectSend(WelcomeListener listener) {
        if (true) {
            listener.onSend();
        } else {
            listener.onUnableToSend();
        }
    }

    @Override
    public void selectReceive(WelcomeListener listener) {
        if (true) {
            listener.onReceive();
        } else {
            listener.onUnableToReceive();
        }
    }
}
