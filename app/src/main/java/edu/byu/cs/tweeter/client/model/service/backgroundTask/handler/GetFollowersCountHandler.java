package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;

public class GetFollowersCountHandler extends Handler {

    public GetFollowersCountHandler() {
        super(Looper.getMainLooper());
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);
        if (success) {
            int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
//            followerCount.setText(getString(R.string.followerCount, String.valueOf(count)));
        } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
//            Toast.makeText(MainActivity.this, "Failed to get followers count: " + message, Toast.LENGTH_LONG).show();
        } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
//            Toast.makeText(MainActivity.this, "Failed to get followers count because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

