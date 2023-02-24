package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User>{
    public FollowingPresenter(View view) {
        super(view);
    }

    @Override
    public String getPresenterText() {
        return "GetFollowing";
    }

    @Override
    public void getItems(User user, int PAGE_SIZE, User lastItem) {
        followService.loadMoreFollowees(user, PAGE_SIZE, lastItem, this);
    }
}