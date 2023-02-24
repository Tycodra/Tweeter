package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class BasePresenter implements ServiceObserver {
    BaseView baseView;

    UserService userService = new UserService();
    FollowService followService = new FollowService();
    StatusService statusService = new StatusService() ;

    public UserService getUserService() {
        return userService;
    }

    public FollowService getFollowService() {
        return followService;
    }

    public StatusService getStatusService() {
        return statusService;
    }

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
