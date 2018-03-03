package io.ermdev.transferit.desktop.welcome;

public class WelcomeInteractImpl implements WelcomeInteract {

    private WelcomeListener wl;

    public WelcomeInteractImpl(WelcomeListener wl)  {
        this.wl = wl;
    }

    @Override
    public void setDisplay() {
        wl.onShow();
    }
}
