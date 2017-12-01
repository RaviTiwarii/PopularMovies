package com.android.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * This activity shows details of a movie.
 */
public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "extra_movie";

    private ImageView mPosterImageView;
    private TextView mTitleTextView;
    private TextView mUserRatingTextView;
    private TextView mDescriptionTextView;
    private TextView mReleaseDateTextView;

    private Movie mMovie;

    public static void start(final Context context, @NonNull final Movie movie) {
        Intent starter = new Intent(context, MovieDetailActivity.class);
        starter.putExtra(EXTRA_MOVIE, movie);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mPosterImageView = findViewById(R.id.iv_poster);
        mTitleTextView = findViewById(R.id.tv_title);
        mUserRatingTextView = findViewById(R.id.tv_user_rating);
        mReleaseDateTextView = findViewById(R.id.tv_release_date);
        mDescriptionTextView = findViewById(R.id.tv_description);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE)) {
            mMovie = intent.getParcelableExtra(EXTRA_MOVIE);
            showMovieDetails();
        }
    }

    private void showMovieDetails() {
        Picasso.with(this)
                .load(mMovie.getPosterPath())
                .into(mPosterImageView);

        mTitleTextView.setText(mMovie.getTitle());
        mUserRatingTextView.setText(String.valueOf(mMovie.getUserRating()));
        mDescriptionTextView.setText(mMovie.getDescription());
        mReleaseDateTextView.setText(mMovie.getReleaseDate());
    }
}
