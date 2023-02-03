package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter {

    public interface View {
        void setLoadingFooter(boolean loadingFooterStatus);
        void displayUser(User user);

        void displayMessage(String message);

        void addMoreItems(List<Status> statusList);
    }
    private View view;
    private StatusService statusService;
    private UserService userService;

    private static final int PAGE_SIZE = 10;
    private boolean isLoading = false;
    private boolean hasMorePages;

    private Status lastStatus;
    public FeedPresenter(View view) {
        this.view = view;
        statusService = new StatusService();
        userService = new UserService();
    }

    public void getUser(String userAlias) {
        userService.getUser(userAlias, new GetUserObserver());
    }
    public boolean hasMorePages() {
        return hasMorePages;
    }
    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }
    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);

            statusService.loadMoreStatuses(user, PAGE_SIZE, lastStatus, new GetFeedObserver());
        }
    }
    public class GetUserObserver implements UserService.GetUserObserver {

        @Override
        public void handleSuccess(User user, AuthToken authToken) {
            view.displayUser(user);
        }

        @Override
        public void handleFailure(String message) {

        }

        @Override
        public void handleException(String message) {
            view.displayMessage(message);
        }

        @Override
        public void displayUser(User user) {
            view.displayUser(user);
        }
    }
    public class GetFeedObserver implements StatusService.FeedObserver {
        @Override
        public void displayError(String message) {

        }

        @Override
        public void displayException(String message) {

        }

        @Override
        public void addStatuses(List<Status> statusList, boolean hasMorePages) {
            lastStatus = (statusList.size() > 0) ? statusList.get(statusList.size() -1) : null;
            setHasMorePages(hasMorePages);
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.addMoreItems(statusList);
        }
    }
}

