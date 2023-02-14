package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends BackgroundTaskHandler<FollowService.IsFollowerObserver> {
    public IsFollowerHandler(FollowService.IsFollowerObserver isFollowerObserver) {
        super(isFollowerObserver);
    }
    @Override
    protected void handleSuccess(Bundle data, FollowService.IsFollowerObserver observer) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        // If logged in user is a follower of the selected user, display the follow button as "following"
        if (isFollower) {
            observer.setIsFollowerButton(true);
        } else {
            observer.setIsFollowerButton(false);
        }
    }
}