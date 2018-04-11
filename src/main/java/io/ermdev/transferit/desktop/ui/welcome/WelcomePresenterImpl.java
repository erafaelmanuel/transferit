package io.ermdev.transferit.desktop.ui.welcome;

import javafx.stage.Stage;

public class WelcomePresenterImpl implements WelcomePresenter, WelcomeInteract.WelcomeListener {

    private WelcomeView view;

    private WelcomeInteract interact;

    WelcomePresenterImpl(WelcomeView view, WelcomeInteract interact) {
        this.view = view;
        this.interact = interact;
    }

    @Override
    public void clickSend(Stage stage) {
        interact.selectSend(stage, this);
    }

    @Override
    public void clickReceive(Stage stage) {
        interact.selectReceive(stage, this);
    }

    @Override
    public void clickOption(double x, double y) {
        interact.selectOption(x, y, this);
    }

    @Override
    public void onSend(Stage stage) {
        view.navigateSend(stage);
    }

    @Override
    public void onReceive(Stage stage) {
        view.navigateReceive(stage);
    }

    @Override
    public void onUnableToSend() {
        view.onErrorSend();
    }

    @Override
    public void onUnableToReceive() {
        view.onErrorReceive();
    }

    @Override
    public void onOption(double x, double y) {
        view.showOptionMenu(x, y);
    }
}
