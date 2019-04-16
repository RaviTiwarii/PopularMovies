package com.android.example.popularmovies.connectivity;

@FunctionalInterface
public interface CallbackFunction<T> {
    void callback(T value);
}
