package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;

public class GetFollowingCountHandler extends BackgroundTaskHandler<FollowService.UpdateFollowingObserver> {
    public GetFollowingCountHandler(FollowService.UpdateFollowingObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccess(Bundle data, FollowService.UpdateFollowingObserver observer) {
        int count = data.getInt(GetFollowingCountTask.COUNT_KEY);
        observer.handleSuccess(count);
    }
}

