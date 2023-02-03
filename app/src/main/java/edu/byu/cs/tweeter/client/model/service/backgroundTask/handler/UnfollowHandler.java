package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;

public class UnfollowHandler extends Handler {
    private UserService.UnfollowObserver observer;
    public UnfollowHandler(UserService.UnfollowObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(UnfollowTask.SUCCESS_KEY);
        if (success) {
            observer.unfollow(true);
        } else if (msg.getData().containsKey(UnfollowTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);
            observer.handleFailure("Failed to unfollow: " + message);
        } else if (msg.getData().containsKey(UnfollowTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);
            observer.handleException("Failed to unfollow because of exception: " + ex.getMessage());
        }
        observer.enableFollowButton(true);
    }
}

