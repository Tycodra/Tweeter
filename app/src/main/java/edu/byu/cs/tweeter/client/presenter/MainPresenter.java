package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {

    public interface View {
        void isFollower(boolean isFollower);
        void displayMessage(String message);

        void unfollow(boolean unfollow);

        void enableFollowButton(boolean enableButton);

        void follow(boolean follow);
    }
    private View view;
    private UserService userService;
    private StatusService statusService;

    public MainPresenter(View view) {
        this.view = view;
        userService = new UserService();
        statusService = new StatusService();
    }

    public void isFollower(User selectedUser) {
        userService.isFollower(selectedUser, new IsFollowerObserver());
    }
    public void unfollow(User selectedUser) {
        userService.unfollow(selectedUser, new UnfollowObserver());
    }
    public void follow(User selectedUser) {
        userService.follow(selectedUser, new FollowObserver());
    }
    
    public class UnfollowObserver implements UserService.UnfollowObserver {

        @Override
        public void handleSuccess(User user, AuthToken authToken) {
            
        }

        @Override
        public void handleFailure(String message) {

        }

        @Override
        public void handleException(String message) {

        }

        @Override
        public void unfollow(boolean unfollow) {
            view.unfollow(unfollow);
        }

        @Override
        public void enableFollowButton(boolean enableButton) {
            view.enableFollowButton(enableButton);
        }
    }
    public class FollowObserver implements UserService.FollowObserver {

        @Override
        public void handleSuccess(User user, AuthToken authToken) {

        }

        @Override
        public void handleFailure(String message) {

        }

        @Override
        public void handleException(String message) {

        }

        @Override
        public void follow(boolean follow) {
            view.follow(follow);
        }

        @Override
        public void enableFollowButton(boolean enable) {
            view.enableFollowButton(enable);
        }
    }
    public class IsFollowerObserver implements UserService.IsFollowerObserver {

        @Override
        public void handleSuccess(User user, AuthToken authToken) {

        }

        @Override
        public void handleFailure(String message) {
            view.displayMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayMessage(message);
        }

        @Override
        public void setIsFollowerButton(boolean isFollower) {
            view.isFollower(isFollower);
        }
    }
}
