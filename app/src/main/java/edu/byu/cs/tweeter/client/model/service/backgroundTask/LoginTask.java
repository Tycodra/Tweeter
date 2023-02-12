package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.util.Log;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LoginHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticationTask {
    private static final String LOG_TAG = "LoginTask";

    public LoginTask(String username, String password, LoginHandler messageHandler) {
        super(messageHandler, username, password);
        this.messageHandler = messageHandler;
    }

    @Override
    protected void logTaskException(Exception ex) {
        Log.e(LOG_TAG, "Failed to login", ex);
    }
@Override
    protected Pair<User, AuthToken> doAuthentication() {
        User loggedInUser = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();
        return new Pair<>(loggedInUser, authToken);
    }
}
