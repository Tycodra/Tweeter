package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends Handler {
    private FollowService.IsFollowerObserver isFollowerObserver;
    public IsFollowerHandler(FollowService.IsFollowerObserver isFollowerObserver) {
        super(Looper.getMainLooper());
        this.isFollowerObserver = isFollowerObserver;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);
        if (success) {
            boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);

            // If logged in user is a follower of the selected user, display the follow button as "following"
            if (isFollower) {
                isFollowerObserver.setIsFollowerButton(true);
            } else {
                isFollowerObserver.setIsFollowerButton(false);
            }
        } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
            isFollowerObserver.displayError("Failed to determine following relationship: " + message);
        } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
            isFollowerObserver.displayException("Failed to determine following relationship because of exception: " + ex.getMessage());
        }
    }
}