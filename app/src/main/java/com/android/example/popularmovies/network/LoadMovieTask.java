package com.android.example.popularmovies.network;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.example.popularmovies.data.model.Movie;
import com.android.example.popularmovies.data.model.MovieType;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoadMovieTask extends AsyncTask<MovieType, Void, List<Movie>> {

    private final WeakReference<Context> contextReference;
    private final PreTaskExecuteListener preTaskExecuteListener;
    private final PostTaskExecuteListener<List<Movie>> postTaskExecuteListener;

    public LoadMovieTask(@NonNull Context context,
                         @Nullable PreTaskExecuteListener preTaskExecuteListener,
                         @NonNull PostTaskExecuteListener<List<Movie>> postTaskExecuteListener) {
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
    protected List<Movie> doInBackground(MovieType... params) {
        Context context = contextReference.get();
        if (params.length == 0 || !NetworkUtils.isInternetAvailable(context)) {
            return null;
        } else {
            MovieType movieType = params[0];
            switch (movieType) {
                case POPULAR:
                    return new MovieFetcher(context).fetchPopularMovies();
                case TOP_RATED:
                    return new MovieFetcher(context).fetchTopRatedMovies();
                default:
                    throw new IllegalArgumentException("Cannot load unknown movie type " + movieType);
            }
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        postTaskExecuteListener.onTaskComplete(movies);
    }
}