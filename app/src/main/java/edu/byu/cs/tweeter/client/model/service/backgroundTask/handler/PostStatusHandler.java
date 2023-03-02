package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PostStatusObserver;

public class PostStatusHandler extends BackgroundTaskHandler<PostStatusObserver> {
    public PostStatusHandler(PostStatusObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccess(Bundle data, PostStatusObserver observer) {
        observer.handleSuccess("Successfully Posted!");
    }
}