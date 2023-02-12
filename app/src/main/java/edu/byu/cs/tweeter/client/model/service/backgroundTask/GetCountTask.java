package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class GetCountTask extends AuthenticatedTask{
    /**
     * The user whose following count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    protected User targetUser;

    private int count = 20;

    public GetCountTask(Handler messageHandler, AuthToken authToken, User targetUser) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putInt(GetFollowersCountTask.COUNT_KEY, count);
    }

    @Override
    protected void runTask() {
        sendSuccessMessage();
    }
}
