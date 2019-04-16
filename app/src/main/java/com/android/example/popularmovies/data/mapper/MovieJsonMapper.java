package com.android.example.popularmovies.data.mapper;

import com.android.example.popularmovies.data.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieJsonMapper implements JsonMapper<Movie> {

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "original_title";
    private static final String KEY_DESCRIPTION = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_USER_RATING = "vote_average";
    private static final String KEY_IMAGE_PATH = "http://image.tmdb.org/t/p/w185/";

    @Override
    public String toJson(Movie type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Movie fromJson(JSONObject movieJson) throws JSONException {
        String id = movieJson.getString(KEY_ID);
        String title = movieJson.getString(KEY_TITLE);
        String description = movieJson.getString(KEY_DESCRIPTION);
        String posterPath = movieJson.getString(KEY_POSTER_PATH);
        String releaseDate = movieJson.getString(KEY_RELEASE_DATE);
        String userRating = movieJson.getString(KEY_USER_RATING);

        return Movie.of(id, title, description, posterPath, releaseDate, userRating);
    }
}
