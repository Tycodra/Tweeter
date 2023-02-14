package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedHandler extends BackgroundTaskHandler<StatusService.FeedObserver> {
    public GetFeedHandler(StatusService.FeedObserver feedObserver) {
        super(feedObserver);
    }
    @Override
    protected void handleSuccess(Bundle data, StatusService.FeedObserver observer) {
        List<Status> statuses = (List<Status>) data.getSerializable(GetFeedTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(GetFeedTask.MORE_PAGES_KEY);
        observer.addStatuses(statuses, hasMorePages);
    }
}