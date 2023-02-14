package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class LogoutHandler extends BackgroundTaskHandler<UserService.LogoutObserver> {
    public LogoutHandler(UserService.LogoutObserver logoutObserver) {
        super(logoutObserver);
    }
    @Override
    protected void handleSuccess(Bundle data, UserService.LogoutObserver observer) {
        observer.logout();
    }
}