package com.android.example.popularmovies.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.android.example.popularmovies.ui.adapter.MovieAdapter;
import com.android.example.popularmovies.data.model.Movie;
import com.android.example.popularmovies.data.model.MovieType;
import com.android.example.popularmovies.network.LoadMovieTask;

import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    private static final String KEY_MOVIE_LIST_STATE = "key_movie_list_state";
    private static final String KEY_SORT_TYPE = "key_sort_type";

    private ProgressBar progressBar;
    private TextView errorMessageTextView;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private LoadMovieTask loadMovieTask;
    private Parcelable movieListState;
    private String selectedMovieType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        progressBar = findViewById(R.id.pb_loading_indicator);
        errorMessageTextView = findViewById(R.id.tv_error_message);
        movieRecyclerView = findViewById(R.id.recycler_view_movie);

        GridLayoutManager layoutManager;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(this, 2);
        else layoutManager = new GridLayoutManager(this, 3);

        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (selectedMovieType == null) selectedMovieType = MovieType.POPULAR;
        loadMovies(selectedMovieType);

        if (movieListState != null)
            movieRecyclerView.getLayoutManager().onRestoreInstanceState(movieListState);
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
                loadMovies(MovieType.FAVORITE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        movieListState = movieRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(KEY_MOVIE_LIST_STATE, movieListState);
        outState.putString(KEY_SORT_TYPE, selectedMovieType);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState == null) return;

        if (savedInstanceState.containsKey(KEY_MOVIE_LIST_STATE))
            movieListState = savedInstanceState.getParcelable(KEY_MOVIE_LIST_STATE);

        if (savedInstanceState.containsKey(KEY_SORT_TYPE))
            selectedMovieType = savedInstanceState.getString(KEY_SORT_TYPE);
    }

    private void startMovieDetailActivity(@NonNull final Movie movie) {
        MovieDetailActivity.start(this, movie);
    }

    private void loadMovies(@NonNull final String type) {
        selectedMovieType = type;
        loadMovieTask = new LoadMovieTask(this, this::onPreTaskExecute, this::onTaskComplete);
        loadMovieTask.execute(type);
    }

    private void onPreTaskExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void onTaskComplete(List<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movies == null || movies.isEmpty()) {
            showEmptyListView();
        } else {
            showMoviesView();
            setupAdapter(movies);
        }
    }

    private void setupAdapter(final List<Movie> movies) {
        if (movieAdapter == null) {
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

    private void showEmptyListView() {
        movieRecyclerView.setVisibility(View.INVISIBLE);
        errorMessageTextView.setVisibility(View.VISIBLE);
    }
}
