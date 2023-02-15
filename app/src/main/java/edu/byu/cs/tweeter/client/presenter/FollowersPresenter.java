package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter {

    public interface View {
        void setLoadingFooter(boolean loadingFooterStatus);
        void displayMessage(String message);

        void addMoreItems(List<User> followers);
        void displayUser(User user);
    }
    private View view;
    private FollowService followService;
    private UserService userService;
    private static final int PAGE_SIZE = 10;
    private User lastFollower;
    private boolean isLoading = false;
    private boolean hasMorePages;

    public FollowersPresenter(View view) {
        this.view = view;
        followService = new FollowService();
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
            followService.loadMoreFollowers(user, PAGE_SIZE, lastFollower, new GetFollowerObserver());
        }
    }

    public class GetUserObserver implements UserService.GetUserObserver {
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

        @Override
        public void handleSuccess() {

        }
    }

    private class GetFollowerObserver implements FollowService.FollowObserver {
//        @Override
//        public void addFollows(List<User> follows, boolean hasMorePages) {
//            lastFollower = (follows.size() > 0) ? follows.get(follows.size() - 1) : null;
//            setHasMorePages(hasMorePages);
//            isLoading = false;
//            view.setLoadingFooter(isLoading);
//            view.addMoreItems(follows);
//        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }

        @Override
        public void handleException(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }

        @Override
        public void handleSuccess(List itemsList, boolean hasMorePages) {
            lastFollower = (itemsList.size() > 0) ? (User) itemsList.get(itemsList.size() - 1) : null;
            setHasMorePages(hasMorePages);
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.addMoreItems(itemsList);
        }
    }
}

