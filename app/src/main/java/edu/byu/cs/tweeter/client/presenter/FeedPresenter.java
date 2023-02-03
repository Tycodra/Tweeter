package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.main.feed.FeedFragment;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter {

    public interface View {

        void displayUser(User user);

        void displayMessage(String message);
    }
    private View view;
    private StatusService statusService;
    private UserService userService;

    private boolean isLoading = false;
    private boolean hasMorePages;

//    private Status lastStatus;
    public FeedPresenter(View view) {
        this.view = view;
        statusService = new StatusService();
        userService = new UserService();
    }

    public void getUser(String userAlias, AuthToken authToken) {
        userService.getUser(userAlias, authToken, new GetUserObserver());
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
    public void loadMoreItems() {
        if (!isLoading) {
            isLoading = true;
//            view.setLoadingFooter(isLoading);
//            statusService.loadMoreStatuses(user, PAGE_SIZE, lastStatus, new )
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
}
