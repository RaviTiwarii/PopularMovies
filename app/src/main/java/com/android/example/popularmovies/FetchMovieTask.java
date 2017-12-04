package com.android.example.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.android.example.popularmovies.model.Movie;
import com.android.example.popularmovies.model.MovieType;
import com.android.example.popularmovies.utilities.MovieFetcher;

import java.util.List;

public class FetchMovieTask extends AsyncTask<MovieType, Void, List<Movie>> {

    private final Context context;
    private final AsyncTaskCallback<List<Movie>> callback;

    public FetchMovieTask(final Context context,
                          @NonNull final AsyncTaskCallback<List<Movie>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onPreStart();
    }

    @Override
    protected List<Movie> doInBackground(MovieType... params) {
        if (params.length == 0) {
            return null;
        } else {
            MovieType movieType = params[0];
            switch (movieType) {
                case POPULAR:
                    return new MovieFetcher(context).fetchPopularMovies();
                case TOP_RATED:
                    return new MovieFetcher(context).fetchTopRatedMovies();
                default:
                    return null;
            }
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        callback.onComplete(movies);
    }
}