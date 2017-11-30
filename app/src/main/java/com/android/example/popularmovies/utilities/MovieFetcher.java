package com.android.example.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.model.Movie;

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

    private static final String API_KEY_PARAM = "api_key";

    private Context mContext;

    public MovieFetcher(final Context context) {
        mContext = context;
    }

    @NonNull
    public List<Movie> fetchPopularMovies() {
        String url = buildUrl(POPULAR_MOVIES_ENDPOINT);
        return downloadMovies(url);
    }

    @Nullable
    public List<Movie> fetchTopRatedMovies() {
        String url = buildUrl(TOP_RATED_MOVIES_ENDPOINT);
        return downloadMovies(url);
    }

    @Nullable
    private String buildUrl(final String endpoint) {
        String apiKey = mContext.getString(R.string.api_key);
        if (apiKey == null) {
            Log.e(TAG, "There is no api key found for TMDB API");
            return null;
        }
        return Uri.parse(endpoint).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build()
                .toString();
    }

    @NonNull
    private List<Movie> downloadMovies(final String url) {
        final List<Movie> movies = new ArrayList<>();
        try {
            final String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            final JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(movies, jsonBody);
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
            return scanner.hasNext() ? scanner.next() : null;
        } finally {
            urlConnection.disconnect();
        }
    }

    @NonNull
    private void parseItems(@NonNull final List<Movie> movies,
                            @NonNull final JSONObject jsonBody) throws JSONException {
        JSONArray movieJsonArray = jsonBody.getJSONArray("results");
        for (int i = 0; i < movieJsonArray.length(); i++) {
            JSONObject movieJsonObject = movieJsonArray.getJSONObject(i);
            Movie movie = Movie.fromJson(movieJsonObject);
            movies.add(movie);
        }
    }
}
