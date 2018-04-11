package io.ermdev.transferit.desktop.ui.welcome;

public class WelcomePresenterImpl implements WelcomePresenter, WelcomeInteract.WelcomeListener {

    private WelcomeView view;
    private WelcomeInteractImpl interact;

    public WelcomePresenterImpl(WelcomeView view, WelcomeInteractImpl interact) {
        this.view = view;
        this.interact = interact;
    }

    @Override
    public void onShow(double x, double y) {
        interact.display(x, y);
    }

    @Override
    public void onSelectSend() {

    }

    @Override
    public void onSelectReceive() {

    }

    @Override
    public void displayWelcome() {

    }

    @Override
    public void clickSend() {
        view.navigateSend();
    }

    @Override
    public void clickReceive() {
        view.navigateReceive();
    }
}
