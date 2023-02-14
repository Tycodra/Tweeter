package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends BackgroundTaskHandler<StatusService.StoryObserver> {
    private StatusService.StoryObserver storyObserver;

    public GetStoryHandler(StatusService.StoryObserver storyObserver) {
        super(storyObserver);
        this.storyObserver = storyObserver;
    }

//    @Override
//    public void handleMessage(@NonNull Message msg) {
//
//        boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
//        if (success) {
//            List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.ITEMS_KEY);
//            boolean hasMorePages = msg.getData().getBoolean(PagedTask.MORE_PAGES_KEY);
//            storyObserver.addStatuses(statuses, hasMorePages);
//        } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
//            storyObserver.displayError("Failed to get story: " + message);
//        } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
//            storyObserver.displayException("Failed to get story because of exception: " + ex.getMessage());
//        }
//    }

    @Override
    protected void handleSuccess(Bundle data, StatusService.StoryObserver observer) {
        List<Status> statuses = (List<Status>) data.getSerializable(GetStoryTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);
        storyObserver.addStatuses(statuses, hasMorePages);
    }
}