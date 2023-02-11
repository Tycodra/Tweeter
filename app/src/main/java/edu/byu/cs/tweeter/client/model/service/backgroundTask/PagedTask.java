package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<T> extends AuthenticatedTask {
    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";

    /**
     * The user whose following is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    protected User targetUser;

    /**
     * Maximum number of followed users to return (i.e., page size).
     */
    private int limit;
    boolean hasMorePages;

    private T lastItem;

    private List<T> items;

    public int getLimit() {
        return limit;
    }
    public T getLastItem() {
        return lastItem;
    }
    public User getTargetUser() {
        return targetUser;
    }


    public PagedTask(Handler messageHandler, AuthToken authToken, User targetUser, int limit, T lastItem) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
        this.limit = limit;
        this.lastItem = lastItem;
    }

    protected abstract Pair<List<T>, Boolean> getItems();
    protected abstract List<User> getUsersForItems(List<T> items);

    @Override
    protected void runTask() {
        Pair<List<T>, Boolean> pageOfItems = getItems();
        items = pageOfItems.getFirst();
        hasMorePages = pageOfItems.getSecond();
    }
    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }
}
