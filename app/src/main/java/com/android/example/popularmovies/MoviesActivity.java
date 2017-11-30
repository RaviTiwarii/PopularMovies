package com.android.example.popularmovies;

import android.os.AsyncTask;
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
import android.widget.Toast;

import com.android.example.popularmovies.model.Movie;
import com.android.example.popularmovies.utilities.MovieFetcher;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity
        implements MovieAdapter.OnListItemClickListener {

    private static final String TAG = MoviesActivity.class.getSimpleName();

    private static final String SORT_TYPE_POPULAR = "sort-type-popular";
    private static final String SORT_TYPE_TOP_RATED = "sort-type-top-rated";

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mMovieRecyclerView = findViewById(R.id.rv_movie);
        mMovieRecyclerView.setLayoutManager(layoutManager);
        mMovieRecyclerView.setHasFixedSize(true);

        setupAdapter(new ArrayList<Movie>());

        loadMovies(SORT_TYPE_POPULAR);
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
                loadMovies(SORT_TYPE_POPULAR);
                return true;
            case R.id.action_top_rated_movie:
                loadMovies(SORT_TYPE_TOP_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(@NonNull final Movie movie) {
        MovieDetailActivity.start(this, movie);
    }

    private void loadMovies(String sortType) {
        showMoviesView();
        new FetchMovieTask(sortType).execute();
    }

    private void setupAdapter(final List<Movie> movies) {
        if (mMovieAdapter == null) {
            mMovieAdapter = new MovieAdapter(movies, this);
            mMovieRecyclerView.setAdapter(mMovieAdapter);
        } else {
            mMovieAdapter.setMovies(movies);
        }
    }

    private void showMoviesView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMovieRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mMovieRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>> {

        private final String SORT_TYPE;

        public FetchMovieTask(String sortType) {
            SORT_TYPE = sortType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            switch (SORT_TYPE) {
                case SORT_TYPE_POPULAR:
                    return new MovieFetcher(MoviesActivity.this).fetchPopularMovies();
                case SORT_TYPE_TOP_RATED:
                    return new MovieFetcher(MoviesActivity.this).fetchTopRatedMovies();
                default:
                    return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies == null || movies.isEmpty()) {
                showErrorMessage();
            } else {
                showMoviesView();
                setupAdapter(movies);
            }
        }
    }

}
