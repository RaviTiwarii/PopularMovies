package com.android.example.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.adapter.ReviewAdapter;
import com.android.example.popularmovies.adapter.TrailerAdapter;
import com.android.example.popularmovies.data.MovieRepository;
import com.android.example.popularmovies.data.model.Movie;
import com.android.example.popularmovies.data.model.Review;
import com.android.example.popularmovies.data.model.Trailer;
import com.android.example.popularmovies.network.LoadReviewTask;
import com.android.example.popularmovies.network.LoadTrailerTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * This activity shows details of a movie.
 */
public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "extra_movie";

    private ImageView posterImageView;
    private ImageView favoriteImageView;
    private TextView titleTextView;
    private TextView movieLengthTextView;
    private TextView userRatingTextView;
    private TextView descriptionTextView;
    private TextView releaseDateTextView;

    private RecyclerView trailerRecyclerView;
    private RecyclerView reviewRecyclerView;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private Movie movie;

    private MovieRepository movieRepository;

    public static void start(final Context context, @NonNull final Movie movie) {
        Intent starter = new Intent(context, MovieDetailActivity.class);
        starter.putExtra(EXTRA_MOVIE, movie);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        movieRepository = new MovieRepository(this);

        posterImageView = findViewById(R.id.iv_movie_poster);
        titleTextView = findViewById(R.id.tv_movie_title);
        movieLengthTextView = findViewById(R.id.tv_movie_length);
        userRatingTextView = findViewById(R.id.tv_movie_rating);
        releaseDateTextView = findViewById(R.id.tv_movie_release_date);
        descriptionTextView = findViewById(R.id.tv_movie_description);

        favoriteImageView = findViewById(R.id.iv_favorite);
        favoriteImageView.setOnClickListener(view -> onFavoriteClicked());

        if (isFavoriteMovie()) favoriteImageView.setSelected(true);

        trailerRecyclerView = findViewById(R.id.rv_movie_trailers);
        setupRecyclerView(trailerRecyclerView);
        setupTrailerAdapter(new ArrayList<>());

        reviewRecyclerView = findViewById(R.id.rv_movie_reviews);
        setupRecyclerView(reviewRecyclerView);
        setupReviewAdapter(new ArrayList<>());

        showMovieDetails();

        new LoadTrailerTask(this, null, this::displayTrailerView)
                .execute(movie.getId());

        new LoadReviewTask(this, null, this::displayReviewView)
                .execute(movie.getId());
    }

    private boolean isFavoriteMovie() {
        return movieRepository.findById(movie.getId()) != null;
    }

    private void onFavoriteClicked() {
        if (favoriteImageView.isSelected()) removeFromFavorites();
        else addToFavorite();
    }

    private void removeFromFavorites() {
        int rowCount = movieRepository.delete(movie.getId());
        boolean isRemoved = rowCount > 0;
        if (isRemoved) {
            favoriteImageView.setSelected(false);
            Toast.makeText(this, getString(R.string.message_removed_favorites),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.message_error_removing_favorites),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void addToFavorite() {
        Uri uriRowInserted = movieRepository.save(movie);
        if (uriRowInserted != null) {
            Toast.makeText(this, getString(R.string.message_added_favorites),
                    Toast.LENGTH_LONG).show();
            favoriteImageView.setSelected(true);
        } else {
            Toast.makeText(this, getString(R.string.message_error_adding_favorites),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        recyclerView.setHasFixedSize(true);
    }

    private void setupTrailerAdapter(List<Trailer> trailers) {
        if (trailerAdapter == null) {
            trailerAdapter = new TrailerAdapter(trailers, this::openInYoutubeApp);
            trailerRecyclerView.setAdapter(trailerAdapter);
        }
        trailerAdapter.setTrailers(trailers);
    }

    private void setupReviewAdapter(List<Review> reviews) {
        if (null == reviewAdapter) {
            reviewAdapter = new ReviewAdapter(reviews);
            reviewRecyclerView.setAdapter(reviewAdapter);
        }
        reviewAdapter.setReviews(reviews);
    }

    private void openInYoutubeApp(Trailer trailer) {
        Uri uri = Uri.parse("vnd.youtube:" + trailer.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    private void showMovieDetails() {
        Picasso.with(this)
                .load(movie.getPosterPathUrl())
                .into(posterImageView);

        titleTextView.setText(movie.getTitle());
        movieLengthTextView.setText(movie.getDuration() + "min");
        userRatingTextView.setText(movie.getUserRating() + "/10");
        descriptionTextView.setText(movie.getDescription());
        releaseDateTextView.setText(movie.getReleaseDate().substring(0, 4));
    }

    private void displayTrailerView(@Nullable List<Trailer> trailers) {
        if (null == trailers)
            Toast.makeText(this, "Fetching trailer failed", Toast.LENGTH_SHORT).show();
        else setupTrailerAdapter(trailers);
    }

    private void displayReviewView(@Nullable List<Review> reviews) {
        if (null == reviews)
            Toast.makeText(this, "Fetching review failed", Toast.LENGTH_SHORT).show();
        else setupReviewAdapter(reviews);
    }
}
