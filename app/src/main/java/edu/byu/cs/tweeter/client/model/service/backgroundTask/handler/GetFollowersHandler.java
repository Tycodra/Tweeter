package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersHandler extends BackgroundTaskHandler<FollowService.FollowObserver> {
    public GetFollowersHandler(FollowService.FollowObserver followObserver) {
        super(followObserver);
    }
    @Override
    protected void handleSuccess(Bundle data, FollowService.FollowObserver observer) {
        List<User> followers = (List<User>) data.getSerializable(GetFollowersTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(GetFollowersTask.MORE_PAGES_KEY);
        observer.addFollows(followers, hasMorePages);
    }
}