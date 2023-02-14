package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {

    public interface View {
        void isFollower(boolean isFollower);
        void displayMessage(String message);

        void unfollow();

        void enableFollowButton(boolean enableButton);

        void follow();

        void logout();

        void cancelPostToast();

        void setFollowerCount(String s);

        void setFollowingCount(String s);
    }
    private View view;
    private UserService userService;
    private StatusService statusService;
    private FollowService followService;

    public MainPresenter(View view) {
        this.view = view;
        userService = new UserService();
        statusService = new StatusService();
        followService = new FollowService();
    }
    public void updateFollows(User selectedUser) {
        followService.updateFollowers(selectedUser, new UpdateFollowersObserver());
        followService.updateFollowing(selectedUser, new UpdateFollowingObserver());
    }

    public void isFollower(User selectedUser) {
        followService.isFollower(selectedUser, new IsFollowerObserver());
    }
    public void unfollow(User selectedUser) {
        followService.unfollow(selectedUser, new UnfollowObserver());
    }
    public void follow(User selectedUser) {
        followService.follow(selectedUser, new FollowObserver());
    }
    public void postStatus(Status newStatus) {
        statusService.postStatus(newStatus, new PostStatusObserver());
    }
    public void logout() {
        userService.logout(new LogoutObserver());
    }
    public class UpdateFollowingObserver implements FollowService.UpdateFollowingObserver {
        @Override
        public void handleFailure(String message) {

        }

        @Override
        public void handleException(String message) {

        }

        @Override
        public void handleSuccess(int count) {
            String followingCount = String.valueOf(count);
            view.setFollowingCount("Following: " + followingCount);
        }
    }
    public class UpdateFollowersObserver implements FollowService.UpdateFollowersObserver {
        @Override
        public void handleFailure(String message) {

        }

        @Override
        public void handleException(String message) {

        }

        @Override
        public void handleSuccess(int count) {
            String followerCount = String.valueOf(count);
            view.setFollowerCount("Followers: " + followerCount);
        }
    }
    public class PostStatusObserver implements StatusService.PostStatusObserver {

        @Override
        public void displaySuccess(String message) {
            view.displayMessage(message);
        }

        @Override
        public void cancelPostToast() {
            view.cancelPostToast();
        }

        @Override
        public void handleFailure(String message) {
            view.displayMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayMessage(message);
        }
    }
    public class LogoutObserver implements UserService.LogoutObserver {

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
        public void logout() {
            view.logout();
        }
    }
    public class UnfollowObserver implements SimpleNotificationObserver {
        @Override
        public void handleSuccess() {
            view.unfollow();
            view.enableFollowButton(true);
        }
        @Override
        public void handleFailure(String message) {
            view.displayMessage(message);
        }
        @Override
        public void handleException(String message) {
            view.displayMessage(message);
        }
    }
    public class FollowObserver implements SimpleNotificationObserver {

        @Override
        public void handleSuccess() {
            view.follow();
            view.enableFollowButton(true);
        }

        @Override
        public void handleFailure(String message) {
            view.displayMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayMessage(message);
        }
    }
    public class IsFollowerObserver implements FollowService.IsFollowerObserver {
        @Override
        public void setIsFollowerButton(boolean isFollower) {
            view.isFollower(isFollower);
        }

        @Override
        public void addFollows(List<User> follows, boolean hasMorePages) {

        }

        @Override
        public void handleFailure(String message) {

        }

        @Override
        public void handleException(String message) {

        }
    }
}
