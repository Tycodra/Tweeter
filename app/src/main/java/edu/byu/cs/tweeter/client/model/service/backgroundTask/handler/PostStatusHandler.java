package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;

public class PostStatusHandler extends Handler {

    public PostStatusHandler() {
        super(Looper.getMainLooper());
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
        if (success) {
//            postingToast.cancel();
//            Toast.makeText(MainActivity.this, "Successfully Posted!", Toast.LENGTH_LONG).show();
        } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
//            Toast.makeText(MainActivity.this, "Failed to post status: " + message, Toast.LENGTH_LONG).show();
        } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
//            Toast.makeText(MainActivity.this, "Failed to post status because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

