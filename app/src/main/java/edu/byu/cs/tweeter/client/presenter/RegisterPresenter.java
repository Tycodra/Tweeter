package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.AuthenticateUserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter implements AuthenticateUserObserver {
    View view;
    public interface View {
        void displayInfoMessage(String message);
        void displayErrorMessage(String message);
        void registerSuccessful(User user, AuthToken authToken);
    }

    public RegisterPresenter(View view) {
        this.view = view;
    }

    public void initiateRegister(String firstName, String lastName, String alias, String password, String imageString) {
        String validationMessage = validateRegistration(firstName, lastName, alias, password, imageString);

        if (validationMessage == null) {
            view.displayInfoMessage("Registering ....");
            UserService service = new UserService();
            service.register(firstName, lastName, alias, password, imageString, this);
        } else {
            view.displayErrorMessage(validationMessage);
        }
    }

    @Override
    public void handleSuccess(User user, AuthToken authToken) {
        view.registerSuccessful(user, authToken);
    }

    @Override
    public void handleFailure(String message) {
        view.displayInfoMessage("Failed to register: " + message);
    }

    @Override
    public void handleException(String message) {
        view.displayErrorMessage("Failed to register because of exception: " + message);
    }

    public String validateRegistration(String firstName, String lastName, String alias, String password, String imageToUpload) {
        if (firstName.length() == 0) {
            return "Firstname cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Lastname cannot be empty.";
        }
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        if (imageToUpload.length() == 0) {
            return "Profile image must be uploaded.";
        }
        return null;
    }
}
