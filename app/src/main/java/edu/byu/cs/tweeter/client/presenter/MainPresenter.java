package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CountObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter{
    public interface View extends BasePresenter.BaseView {
        void isFollower(boolean isFollower);
        void displayMessage(String message);

        void unfollow();

        void follow();

        void enableFollowButton(boolean enableButton);

        void logout();

        void cancelPostToast();

        void setFollowerCount(String string);

        void setFollowingCount(String string);
    }
    public View view;
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
        followService.updateFollowers(selectedUser, new UpdateFollowersObserver(view));
        followService.updateFollowing(selectedUser, new UpdateFollowingObserver(view));
    }

    public void isFollower(User selectedUser) {
        followService.isFollower(selectedUser, new IsFollowerObserver(view));
    }
    public void unfollow(User selectedUser) {
        followService.unfollow(selectedUser, new UnfollowObserver(view));
    }
    public void follow(User selectedUser) {
        followService.follow(selectedUser, new FollowObserver(view));
    }
    public void postStatus(Status newStatus) {
        statusService.postStatus(newStatus, new PostStatusObserver(view));
    }
    public void logout() {
        userService.logout(new LogoutObserver(view));
    }
    public class UpdateFollowingObserver extends BasePresenter implements CountObserver {
        MainPresenter.View view;
        public UpdateFollowingObserver(MainPresenter.View view) {
            super(view);
            this.view = view;
        }

        @Override
        public String getPresenterText() {
            return "get Following";
        }

        @Override
        public void handleSuccess(int count) {
            String followingCount = String.valueOf(count);
            view.setFollowingCount("Following: " + followingCount);
        }
    }
    public class UpdateFollowersObserver extends BasePresenter implements CountObserver {
        MainPresenter.View view;
        public UpdateFollowersObserver(MainPresenter.View view) {
            super(view);
            this.view = view;
        }

        @Override
        public String getPresenterText() {
            return "get Followers";
        }

        @Override
        public void handleSuccess(int count) {
            String followerCount = String.valueOf(count);
            view.setFollowerCount("Followers: " + followerCount);
        }
    }
    public class PostStatusObserver extends BasePresenter implements StatusService.PostStatusObserver {
        MainPresenter.View view;
        public PostStatusObserver(MainPresenter.View view) {
            super(view);
            this.view = view;
        }
        @Override
        public void handleSuccess(String message) {
            view.cancelPostToast();
            view.displayMessage(message);
        }
        @Override
        public String getPresenterText() {
            return "Post Status";
        }
    }
    public class LogoutObserver extends BasePresenter implements SimpleNotificationObserver {
        MainPresenter.View view;
        public LogoutObserver(MainPresenter.View view) {
            super(view);
            this.view = view;
        }

        @Override
        public String getPresenterText() {
            return "Logout";
        }

        @Override
        public void handleSuccess() {
            view.logout();
        }
    }
    public class UnfollowObserver extends BasePresenter implements SimpleNotificationObserver {
        MainPresenter.View view;
        public UnfollowObserver(MainPresenter.View view) {
            super(view);
            this.view = view;
        }

        @Override
        public void handleSuccess() {
            view.unfollow();
            view.enableFollowButton(true);
        }
        @Override
        public String getPresenterText() {
            return "Failed to Unfollow";
        }
    }
    public class FollowObserver extends BasePresenter implements SimpleNotificationObserver {
        MainPresenter.View view;
        public FollowObserver(MainPresenter.View view) {
            super(view);
            this.view = view;
        }

        @Override
        public void handleSuccess() {
            view.follow();
            view.enableFollowButton(true);
        }

        @Override
        public String getPresenterText() {
            return "Follow";
        }
    }
    public class IsFollowerObserver extends BasePresenter implements FollowService.IsFollowerObserver {
        MainPresenter.View view;
        public IsFollowerObserver(MainPresenter.View view) {
            super(view);
            this.view = view;
        }

        @Override
        public void setIsFollowerButton(boolean isFollower) {
            view.isFollower(isFollower);
        }

        @Override
        public String getPresenterText() {
            return "IsFollower";
        }
    }
}
