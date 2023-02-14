package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingHandler extends BackgroundTaskHandler<FollowService.FollowObserver> {
    public GetFollowingHandler(FollowService.FollowObserver followObserver) {
        super(followObserver);
    }
    @Override
    protected void handleSuccess(Bundle data, FollowService.FollowObserver observer) {
        List<User> followees = (List<User>) data.getSerializable(GetFollowingTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(GetFollowingTask.MORE_PAGES_KEY);
        observer.addFollows(followees, hasMorePages);
    }
}