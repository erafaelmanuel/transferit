package io.ermdev.transferit.desktop.ui.welcome;

public class WelcomeInteractImpl implements WelcomeInteract {

    private WelcomeListener wl;

    public WelcomeInteractImpl(WelcomeListener wl)  {
        this.wl = wl;
    }

    @Override
    public void setDisplay(double x, double y) {
        wl.onShow(x, y);
    }
}
