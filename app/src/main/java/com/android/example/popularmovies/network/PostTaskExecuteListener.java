package com.android.example.popularmovies.network;

@FunctionalInterface
public interface PostTaskExecuteListener<T> {
    void onTaskComplete(T t);
}
