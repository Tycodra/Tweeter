package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.StatusService;

public class PostStatusHandler extends BackgroundTaskHandler<StatusService.PostStatusObserver> {
    public PostStatusHandler(StatusService.PostStatusObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccess(Bundle data, StatusService.PostStatusObserver observer) {
        observer.cancelPostToast();
        observer.displaySuccess("Successfully Posted!");
    }
}