package com.android.example.popularmovies.network;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.example.popularmovies.connectivity.NetworkUtils;
import com.android.example.popularmovies.data.model.Trailer;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoadTrailerTask extends AsyncTask<String, Void, List<Trailer>> {

    private final WeakReference<Context> contextReference;
    private final PreTaskExecuteListener preTaskExecuteListener;
    private final PostTaskExecuteListener<List<Trailer>> postTaskExecuteListener;

    public LoadTrailerTask(@NonNull Context context,
                           @Nullable PreTaskExecuteListener preTaskExecuteListener,
                           @NonNull PostTaskExecuteListener<List<Trailer>> postTaskExecuteListener) {
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
    protected List<Trailer> doInBackground(String... params) {
        Context context = contextReference.get();
        if (params.length != 0 && NetworkUtils.isInternetAvailable(context)) {
            String movieId = params[0];
            return new MovieFetcher(context).fetchMovieTrailers(movieId);
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(@Nullable List<Trailer> trailers) {
        postTaskExecuteListener.onTaskComplete(trailers);
    }
}