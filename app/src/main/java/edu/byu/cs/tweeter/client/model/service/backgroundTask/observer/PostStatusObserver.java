package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

public interface PostStatusObserver extends ServiceObserver{
    void handleSuccess(String message);
}
