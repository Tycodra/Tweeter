package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagedTaskHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CountObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedTaskObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {
    public interface FollowObserver extends PagedTaskObserver {
//        void addFollows(List<User> follows, boolean hasMorePages);
    }
    public interface IsFollowerObserver extends FollowObserver{
        void setIsFollowerButton(boolean isFollower);
    }
    public interface UpdateFollowersObserver extends ServiceObserver {
        void handleSuccess(int count);
    }
    public interface UpdateFollowingObserver extends ServiceObserver {
        void handleSuccess(int count);
    }
    public void loadMoreFollowees(User user, int pageSize, User lastFollow, FollowObserver followObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollow, new PagedTaskHandler<User>(followObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }
    public void loadMoreFollowers(User user, int pageSize, User lastFollow, FollowObserver followObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollow, new PagedTaskHandler<User>(followObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }
    public void isFollower(User selectedUser, FollowService.IsFollowerObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }
    public void follow(User selectedUser, SimpleNotificationObserver observer) {
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleNotificationHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }
    public void unfollow(User selectedUser, SimpleNotificationObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleNotificationHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }
    public void updateFollowers(User selectedUser, CountObserver countObserver) {
        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetCountHandler(countObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followersCountTask);
    }
    public void updateFollowing(User selectedUser, CountObserver countObserver) {
        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetCountHandler(countObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followingCountTask);
    }
}
