package io.ermdev.transferit.desktop.ui.welcome;

public class WelcomeInteractImpl implements WelcomeInteract {

    private WelcomeListener listener;

    public WelcomeInteractImpl(WelcomeListener listener) {
        this.listener = listener;
    }

    @Override
    public void display(double x, double y) {
        listener.onShow(x, y);
    }

    @Override
    public void selectSend() {
        listener.onSelectSend();
    }

    @Override
    public void selectReceive() {
        listener.onSelectReceive();
    }
}
