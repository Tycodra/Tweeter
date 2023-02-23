package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends BasePresenter implements UserService.GetUserObserver{
    private UserService userService;
    protected static final int PAGE_SIZE = 10;
    protected boolean isLoading = false;
    protected boolean hasMorePages;
    protected T lastItem;

    public PagedPresenter(BaseView view) {
        super(view);
        userService = new UserService();
    }
    public void loadMoreItems(User user) {
        if (!isLoading) {
            isLoading = true;
//            view.setLoadingFooter(isLoading);
            getItems(user, PAGE_SIZE, lastItem);
        }
    }
    public void getUser(String userAlias) {
//        userService.getUser(userAlias, new GetUserObserver());
    }
    public abstract void getItems(User user, int PAGE_SIZE, T lastItem);

}
