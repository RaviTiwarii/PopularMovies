package com.android.example.popularmovies.network;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.example.popularmovies.connectivity.NetworkUtils;
import com.android.example.popularmovies.data.model.Review;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoadReviewTask extends AsyncTask<String, Void, List<Review>> {

    private final WeakReference<Context> contextReference;
    private final PreTaskExecuteListener preTaskExecuteListener;
    private final PostTaskExecuteListener<List<Review>> postTaskExecuteListener;

    public LoadReviewTask(@NonNull Context context,
                          @Nullable PreTaskExecuteListener preTaskExecuteListener,
                          @NonNull PostTaskExecuteListener<List<Review>> postTaskExecuteListener) {
        this.contextReference = new WeakReference<>(context);
        this.preTaskExecuteListener = preTaskExecuteListener;
        this.postTaskExecuteListener = postTaskExecuteListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (null != preTaskExecuteListener) preTaskExecuteListener.onPreTaskExecute();
    }

    @Override
    @Nullable
    protected List<Review> doInBackground(String... params) {
        Context context = contextReference.get();
        if (params.length != 0 && NetworkUtils.isInternetAvailable(context)) {
            String movieId = params[0];
            return new MovieFetcher(context).fetchMovieReviews(movieId);
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(@Nullable List<Review> reviews) {
        postTaskExecuteListener.onTaskComplete(reviews);
    }
}