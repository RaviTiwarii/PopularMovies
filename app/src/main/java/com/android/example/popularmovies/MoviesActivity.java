package com.android.example.popularmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.example.popularmovies.model.Movie;
import com.android.example.popularmovies.model.MovieType;
import com.android.example.popularmovies.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity
        implements MovieAdapter.OnListItemClickListener {

    private ProgressBar loadingIndicator;
    private TextView errorMessageDisplay;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private FetchMovieTask fetchMovieTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        loadingIndicator = findViewById(R.id.pb_loading_indicator);
        errorMessageDisplay = findViewById(R.id.tv_error_message_display);
        movieRecyclerView = findViewById(R.id.rv_movie);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setHasFixedSize(true);

        setupAdapter(new ArrayList<Movie>());

        loadMovies(MovieType.POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular_movie:
                loadMovies(MovieType.POPULAR);
                return true;
            case R.id.action_top_rated_movie:
                loadMovies(MovieType.TOP_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(@NonNull final Movie movie) {
        MovieDetailActivity.start(this, movie);
    }

    private void loadMovies(@NonNull final MovieType movieType) {
        if (NetworkUtils.isInternetAvailable(this)) {
            showMoviesView();
            fetchMovieTask = createFetchMovieTask();
            fetchMovieTask.execute(movieType);
        } else {
            showErrorView();
        }
    }

    @NonNull
    private FetchMovieTask createFetchMovieTask() {
        return new FetchMovieTask(this, new AsyncTaskCallback<List<Movie>>() {
            @Override
            public void onPreStart() {
                loadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onComplete(List<Movie> movies) {
                loadingIndicator.setVisibility(View.INVISIBLE);
                if (movies == null || movies.isEmpty()) {
                    showErrorView();
                } else {
                    showMoviesView();
                    setupAdapter(movies);
                }
            }
        });
    }

    private void setupAdapter(final List<Movie> movies) {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(movies, this);
            movieRecyclerView.setAdapter(movieAdapter);
        } else {
            movieAdapter.setMovies(movies);
        }
    }

    private void showMoviesView() {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        movieRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {
        movieRecyclerView.setVisibility(View.INVISIBLE);
        errorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
