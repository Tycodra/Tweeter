package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CountObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends BasePresenter{
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
    private UserService userService;
    private StatusService statusService;
    private FollowService followService;

    public MainPresenter(View view) {
        super(view);
        userService = new UserService();
        statusService = new StatusService();
        followService = new FollowService();
    }
    public View getMainView() {
        return (View)baseView;
    }
    public void updateFollows(User selectedUser) {
        followService.updateFollowers(selectedUser, new UpdateFollowersObserver(getMainView()));
        followService.updateFollowing(selectedUser, new UpdateFollowingObserver(getMainView()));
    }

    public void isFollower(User selectedUser) {
        followService.isFollower(selectedUser, new IsFollowerObserver(getMainView()));
    }
    public void unfollow(User selectedUser) {
        followService.unfollow(selectedUser, new UnfollowObserver(getMainView()));
    }
    public void follow(User selectedUser) {
        followService.follow(selectedUser, new FollowObserver(getMainView()));
    }
    public void postStatus(Status newStatus) {
        statusService.postStatus(newStatus, new PostStatusObserver(getMainView()));
    }
    public void logout() {
        userService.logout(new LogoutObserver(getMainView()));
    }

    public class UpdateFollowingObserver extends BasePresenter implements CountObserver {
        public UpdateFollowingObserver(MainPresenter.View view) {
            super(view);
        }
        @Override
        public String getPresenterText() {
            return "get Following";
        }
        @Override
        public void handleSuccess(int count) {
            String followingCount = String.valueOf(count);
            getMainView().setFollowingCount("Following: " + followingCount);
        }
    }

    public class UpdateFollowersObserver extends BasePresenter implements CountObserver {
        public UpdateFollowersObserver(MainPresenter.View view) {
            super(view);
        }
        @Override
        public String getPresenterText() {
            return "get Followers";
        }
        @Override
        public void handleSuccess(int count) {
            String followerCount = String.valueOf(count);
            getMainView().setFollowerCount("Followers: " + followerCount);
        }
    }

    public class PostStatusObserver extends BasePresenter implements StatusService.PostStatusObserver {
        public PostStatusObserver(MainPresenter.View view) {
            super(view);
        }
        @Override
        public void handleSuccess(String message) {
            getMainView().cancelPostToast();
            getMainView().displayMessage(message);
        }
        @Override
        public String getPresenterText() {
            return "Post Status";
        }
    }

    public class LogoutObserver extends BasePresenter implements SimpleNotificationObserver {
        public LogoutObserver(MainPresenter.View view) {
            super(view);
        }
        @Override
        public String getPresenterText() {
            return "Logout";
        }
        @Override
        public void handleSuccess() {
            getMainView().logout();
        }
    }

    public class UnfollowObserver extends BasePresenter implements SimpleNotificationObserver {
        public UnfollowObserver(MainPresenter.View view) {
            super(view);
        }
        @Override
        public void handleSuccess() {
            getMainView().unfollow();
            getMainView().enableFollowButton(true);
        }
        @Override
        public String getPresenterText() {
            return "Failed to Unfollow";
        }
    }

    public class FollowObserver extends BasePresenter implements SimpleNotificationObserver {
        public FollowObserver(MainPresenter.View view) {
            super(view);
        }
        @Override
        public void handleSuccess() {
            getMainView().follow();
            getMainView().enableFollowButton(true);
        }
        @Override
        public String getPresenterText() {
            return "Follow";
        }
    }

    public class IsFollowerObserver extends BasePresenter implements FollowService.IsFollowerObserver {
        public IsFollowerObserver(MainPresenter.View view) {
            super(view);
        }
        @Override
        public void setIsFollowerButton(boolean isFollower) {
            getMainView().isFollower(isFollower);
        }
        @Override
        public String getPresenterText() {
            return "IsFollower";
        }
    }

    @Override
    public String getPresenterText() {
        return null;
    }
}
