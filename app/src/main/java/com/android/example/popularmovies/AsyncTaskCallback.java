package com.android.example.popularmovies;

public interface AsyncTaskCallback<T> {
    void onPreStart();
    void onComplete(T result);
}
