package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends BackgroundTaskHandler<StatusService.StoryObserver> {
    public GetStoryHandler(StatusService.StoryObserver storyObserver) {
        super(storyObserver);
    }
    @Override
    protected void handleSuccess(Bundle data, StatusService.StoryObserver observer) {
        List<Status> statuses = (List<Status>) data.getSerializable(GetStoryTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);
        observer.addStatuses(statuses, hasMorePages);
    }
}