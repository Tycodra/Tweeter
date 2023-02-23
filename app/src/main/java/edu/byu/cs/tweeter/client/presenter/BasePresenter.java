package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.ServiceObserver;

public abstract class BasePresenter implements ServiceObserver {
    BaseView baseView;

    public interface BaseView {
        void displayInfoMessage(String message);
        void displayErrorMessage(String message);
    }

    public BasePresenter(BaseView view) {
        this.baseView = view;
    }

    @Override
    public void handleFailure(String message) {
        baseView.displayInfoMessage("Failed to " + getPresenterText() +": " + message);
    }

    @Override
    public void handleException(String message) {
        baseView.displayErrorMessage("Failed to " + getPresenterText() +" because of exception: " + message);
    }

    public abstract String getPresenterText();
}
