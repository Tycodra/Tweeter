package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter implements UserService.UserObserver{
    View view;

    public RegisterPresenter(View view) {
        this.view = view;
    }

    public void initiateRegister(String firstName, String lastName, String alias, String password, ImageView imageToUpload) {
        String validationMessage = validateRegistration(firstName, lastName, alias, password, imageToUpload);

        if (validationMessage == null) {
            view.displayInfoMessage("Registering ....");
            UserService service = new UserService();

            // Convert image to byte array.
            Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageBytes = bos.toByteArray();

            // Intentionally, Use the java Base64 encoder so it is compatible with M4.
            String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);

            service.register(firstName, lastName, alias, password, imageBytesBase64, this);
        } else {
            view.displayErrorMessage(validationMessage);
        }
    }

    public String validateRegistration(String firstName, String lastName, String alias, String password, ImageView imageToUpload) {
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
        if (imageToUpload.getDrawable() == null) {
            return "Profile image must be uploaded.";
        }
        return null;
    }

    @Override
    public void handleSuccess(User user, AuthToken authToken) {
        view.registerSuccessful(user, authToken);
    }

    @Override
    public void handleFailure(String message) {
        view.displayInfoMessage(message);
    }

    @Override
    public void handleException(String message) {
        view.displayErrorMessage(message);
    }

    public interface View {

        void displayInfoMessage(String s);

        void displayErrorMessage(String validationMessage);

        void registerSuccessful(User user, AuthToken authToken);
    }
}
