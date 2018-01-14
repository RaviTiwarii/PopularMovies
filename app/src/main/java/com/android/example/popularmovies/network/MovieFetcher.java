package com.android.example.popularmovies.network;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.model.Movie;
import com.android.example.popularmovies.data.model.Review;
import com.android.example.popularmovies.data.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieFetcher {

    private static final String TAG = MovieFetcher.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULAR_MOVIES_ENDPOINT = BASE_URL + "popular";
    private static final String TOP_RATED_MOVIES_ENDPOINT = BASE_URL + "top_rated";
    private static final String TRAILERS_ENDPOINT = BASE_URL + "{id}/videos";
    private static final String REVIEWS_ENDPOINT = BASE_URL + "{id}/reviews";
    private static final String API_KEY_PARAM = "api_key";

    private final Context context;

    public MovieFetcher(final Context context) {
        this.context = context;
    }

    @NonNull
    public List<Movie> fetchPopularMovies() {
        String url = buildUrl(POPULAR_MOVIES_ENDPOINT);
        return fetchMovies(url);
    }

    @NonNull
    public List<Movie> fetchTopRatedMovies() {
        String url = buildUrl(TOP_RATED_MOVIES_ENDPOINT);
        return fetchMovies(url);
    }

    public List<Trailer> fetchMovieTrailers(String id) {
        String urlString = buildUrl(TRAILERS_ENDPOINT.replace("{id}", id));
        List<Trailer> trailers = new ArrayList<>();
        try {
            final String trailersJson = getUrlString(urlString);
            if (null != trailersJson && !trailersJson.isEmpty())
                parseTrailers(trailers, trailersJson);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return trailers;
    }

    public List<Review> fetchMovieReviews(String id) {
        String urlString = buildUrl(REVIEWS_ENDPOINT.replace("{id}", id));
        List<Review> reviews = new ArrayList<>();
        try {
            final String reviewsJson = getUrlString(urlString);
            if (null != reviewsJson && !reviewsJson.isEmpty())
                parseReviews(reviews, reviewsJson);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return reviews;
    }


    @NonNull
    private String buildUrl(@NonNull final String endpoint) {
        String apiKey = context.getString(R.string.api_key);
        return Uri.parse(endpoint).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build()
                .toString();
    }

    @NonNull
    private List<Movie> fetchMovies(final String url) {
        List<Movie> movies = new ArrayList<>();
        try {
            String jsonString = getUrlString(url);
            if (null != jsonString && !jsonString.isEmpty())
                parseMovies(movies, jsonString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return movies;
    }

    @Nullable
    private String getUrlString(@NonNull final String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        Log.i(TAG, "URL Created: " + url.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            scanner.useDelimiter("\\A");
            String jsonString = scanner.hasNext() ? scanner.next() : null;
            Log.d(TAG, "Received JSON: " + jsonString);
            return jsonString;
        } finally {
            urlConnection.disconnect();
        }
    }

    private void parseMovies(@NonNull final List<Movie> movies,
                             @NonNull final String jsonString) throws JSONException {
        JSONObject moviesJson = new JSONObject(jsonString);
        JSONArray movieJsonArray = moviesJson.getJSONArray("results");
        for (int i = 0; i < movieJsonArray.length(); i++) {
            JSONObject movieJson = movieJsonArray.getJSONObject(i);
            Movie movie = Movie.fromJson(movieJson);
            movie.setLength(getLengthOfMovie(movie.getId()));
            movies.add(movie);
        }
    }

    private String getLengthOfMovie(String id) {
        String endpoint = BASE_URL + id;
        String urlString = buildUrl(endpoint);
        try {
            String jsonString = getUrlString(urlString);
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject.getString("runtime");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseTrailers(@NonNull final List<Trailer> trailers,
                               @NonNull final String jsonString) throws JSONException {
        JSONObject trailersJson = new JSONObject(jsonString);
        JSONArray trailerJsonArray = trailersJson.getJSONArray("results");
        for (int i = 0; i < trailerJsonArray.length(); i++) {
            JSONObject trailerJson = trailerJsonArray.getJSONObject(i);
            Trailer trailer = Trailer.from(trailerJson);
            trailers.add(trailer);
        }
    }

    private void parseReviews(@NonNull final List<Review> reviews,
                              @NonNull final String jsonString) throws JSONException {
        JSONObject reviewsJson = new JSONObject(jsonString);
        JSONArray reviewsJsonArray = reviewsJson.getJSONArray("results");
        for (int i = 0; i < reviewsJsonArray.length(); i++) {
            JSONObject reviewJson = reviewsJsonArray.getJSONObject(i);
            Review review = Review.from(reviewJson);
            reviews.add(review);
        }
    }
}
