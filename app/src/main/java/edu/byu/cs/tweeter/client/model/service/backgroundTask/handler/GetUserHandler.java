package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends BackgroundTaskHandler<UserService.GetUserObserver> {
    public GetUserHandler(UserService.GetUserObserver getUserObserver) {
        super(getUserObserver);
    }
    @Override
    protected void handleSuccess(Bundle data, UserService.GetUserObserver observer) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);
        observer.displayUser(user);
    }
}