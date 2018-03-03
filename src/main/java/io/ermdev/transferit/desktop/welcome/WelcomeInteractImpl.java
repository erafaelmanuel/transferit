package io.ermdev.transferit.desktop.welcome;

public class WelcomeInteractImpl implements WelcomeInteract {

    private WelcomeListener el;

    public WelcomeInteractImpl(WelcomeListener el)  {
        this.el = el;
    }

    @Override
    public void setDisplay() {
        el.onShow();
    }
}
