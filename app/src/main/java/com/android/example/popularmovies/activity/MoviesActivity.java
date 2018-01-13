package com.android.example.popularmovies.activity;

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

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.adapter.MovieAdapter;
import com.android.example.popularmovies.data.MovieRepository;
import com.android.example.popularmovies.data.model.Movie;
import com.android.example.popularmovies.data.model.MovieType;
import com.android.example.popularmovies.network.LoadMovieTask;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    private ProgressBar loadingIndicator;
    private TextView errorMessageTextView;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private LoadMovieTask loadMovieTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        loadingIndicator = findViewById(R.id.pb_loading_indicator);
        errorMessageTextView = findViewById(R.id.tv_error_message);
        movieRecyclerView = findViewById(R.id.recyclerview_movie);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieRecyclerView.setHasFixedSize(true);

        setupAdapter(new ArrayList<>());
        loadMovies(MovieType.POPULAR);
    }

    @Override
    protected void onDestroy() {
        if (loadMovieTask != null)
            loadMovieTask.cancel(true);
        super.onDestroy();
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
            case R.id.action_popular_movies:
                loadMovies(MovieType.POPULAR);
                return true;
            case R.id.action_top_rated_movies:
                loadMovies(MovieType.TOP_RATED);
                return true;
            case R.id.action_favorite_movies:
                loadMovies(MovieType.FAVORITES);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startMovieDetailActivity(@NonNull final Movie movie) {
        MovieDetailActivity.start(this, movie);
    }

    private void loadMovies(@NonNull final MovieType movieType) {
        if (movieType == MovieType.FAVORITES) {
            onTaskComplete(new MovieRepository(this).findAll());
            return;
        }
        loadMovieTask =
                new LoadMovieTask(this, this::onPreTaskExecute, this::onTaskComplete);
        loadMovieTask.execute(movieType);
    }

    private void onPreTaskExecute() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void onTaskComplete(List<Movie> movies) {
        loadingIndicator.setVisibility(View.INVISIBLE);
        if (null == movies || movies.isEmpty()) {
            showErrorView();
        } else {
            showMoviesView();
            setupAdapter(movies);
        }
    }

    private void setupAdapter(final List<Movie> movies) {
        if (null == movieAdapter) {
            movieAdapter = new MovieAdapter(movies, this::startMovieDetailActivity);
            movieRecyclerView.setAdapter(movieAdapter);
        } else {
            movieAdapter.setMovies(movies);
        }
    }

    private void showMoviesView() {
        errorMessageTextView.setVisibility(View.INVISIBLE);
        movieRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {
        movieRecyclerView.setVisibility(View.INVISIBLE);
        errorMessageTextView.setVisibility(View.VISIBLE);
    }
}
