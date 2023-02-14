package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.ServiceObserver;

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

