package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;

public class GetFollowersCountHandler extends BackgroundTaskHandler<FollowService.UpdateFollowersObserver> {
    public GetFollowersCountHandler(FollowService.UpdateFollowersObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccess(Bundle data, FollowService.UpdateFollowersObserver observer) {
        int count = data.getInt(GetFollowersCountTask.COUNT_KEY);
        observer.handleSuccess(count);
    }
}