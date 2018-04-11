package io.ermdev.transferit.desktop.ui.welcome.v2;

import io.ermdev.transferit.desktop.ui.welcome.WelcomeView;

public class WelcomePresenterImpl implements WelcomePresenter, WelcomeInteract.WelcomeListener {

    private WelcomeView view;

    private WelcomeInteractImpl interact;

    public WelcomePresenterImpl(WelcomeView view, WelcomeInteractImpl interact) {
        this.view = view;
        this.interact = interact;
    }

    @Override
    public void clickSend() {
        interact.selectSend(this);
    }

    @Override
    public void clickReceive() {
        interact.selectReceive(this);
    }

    @Override
    public void onSend() {
        view.navigateSend();
    }

    @Override
    public void onReceive() {
        view.navigateReceive();
    }

    @Override
    public void onUnableToSend() {
        view.onErrorSend();
    }

    @Override
    public void onUnableToReceive() {
        view.onErrorReceive();
    }
}
