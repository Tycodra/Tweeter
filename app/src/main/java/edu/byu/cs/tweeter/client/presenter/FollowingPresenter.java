package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter {

    public interface View {
        void setLoadingFooter(boolean loadingFooterStatus);
        void displayMessage(String message);

        void addMoreItems(List<User> followees);
        void displayUser(User user);
    }
    private View view;
    private FollowService followService;
    private UserService userService;
    private static final int PAGE_SIZE = 10;

    private User lastFollowee;
    private boolean isLoading = false;
    private boolean hasMorePages;

    public FollowingPresenter(View view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
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

    public void getUser(String userAlias, AuthToken authToken) {
        userService.getUser(userAlias, authToken, new GetUserObserver());
    }
    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);
            followService.loadMoreFollowees(user, PAGE_SIZE, lastFollowee, new GetFollowingObserver());
        }
    }
public class GetUserObserver implements UserService.GetUserObserver {

    @Override
    public void handleSuccess(User user, AuthToken authToken) {

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
    private class GetFollowingObserver implements FollowService.FollowObserver {

        @Override
        public void displayError(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }

        @Override
        public void displayException(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }

        @Override
        public void addFollows(List<User> follows, boolean hasMorePages) {
            lastFollowee = (follows.size() > 0) ? follows.get(follows.size() - 1) : null;
            setHasMorePages(hasMorePages);
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.addMoreItems(follows);
        }
    }
}
