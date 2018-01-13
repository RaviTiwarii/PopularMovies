package com.android.example.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "original_title";
    private static final String KEY_DESCRIPTION = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_USER_RATING = "vote_average";
    private static final String KEY_IMAGE_PATH = "http://image.tmdb.org/t/p/w185/";

    private final String id;
    private final String title;
    private final String description;
    private final String posterPath;
    private final String releaseDate;
    private final String userRating;
    private String duration;

    private Movie(String id, String title, String description, String posterPath,
                  String releaseDate, String userRating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
    }

    private Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        userRating = in.readString();
        duration = in.readString();
    }

    public static Movie of(String id, String title, String description, String posterPath,
                           String releaseDate, String userRating) {
        return new Movie(id, title, description, posterPath, releaseDate, userRating);
    }

    @NonNull
    public static Movie fromJson(JSONObject movieJson) throws JSONException {
        String id = movieJson.getString(KEY_ID);
        String title = movieJson.getString(KEY_TITLE);
        String description = movieJson.getString(KEY_DESCRIPTION);
        String posterPath = movieJson.getString(KEY_POSTER_PATH);
        String releaseDate = movieJson.getString(KEY_RELEASE_DATE);
        String userRating = movieJson.getString(KEY_USER_RATING);

        return Movie.of(id, title, description, posterPath, releaseDate, userRating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(userRating);
        dest.writeString(duration);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getPosterPathUrl() {
        return KEY_IMAGE_PATH + posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
