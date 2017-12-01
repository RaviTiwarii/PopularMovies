package com.android.example.popularmovies.model;

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

    private static final String TMDB_MOVIE_ID = "id";
    private static final String TMDB_MOVIE_TITLE = "original_title";
    private static final String TMDB_MOVIE_DESCRIPTION = "overview";
    private static final String TMDB_MOVIE_POSTER_PATH = "poster_path";
    private static final String TMDB_MOVIE_RELEASE_DATE = "release_date";
    private static final String TMDB_MOVIE_USER_RATING = "vote_average";
    private static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w185/";

    private final long mId;
    private final String mTitle;
    private final String mDescription;
    private final String mPosterPath;
    private final String mReleaseDate;
    private final float mUserRating;

    private Movie(long id, String title, String description, String posterPath,
                  String releaseDate, float userRating) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mPosterPath = posterPath;
        mReleaseDate = releaseDate;
        mUserRating = userRating;
    }

    private Movie(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mDescription = in.readString();
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mUserRating = in.readFloat();
    }

    public static Movie of(long id, String title, String description, String posterPath,
                           String releaseDate, float userRating) {
        return new Movie(id, title, description, posterPath, releaseDate, userRating);
    }

    @NonNull
    public static Movie fromJson(JSONObject movieJsonObject) throws JSONException {
        long id = movieJsonObject.getLong(TMDB_MOVIE_ID);
        String title = movieJsonObject.getString(TMDB_MOVIE_TITLE);
        String description = movieJsonObject.getString(TMDB_MOVIE_DESCRIPTION);
        String posterPath = movieJsonObject.getString(TMDB_MOVIE_POSTER_PATH);
        String releaseDate = movieJsonObject.getString(TMDB_MOVIE_RELEASE_DATE);
        float userRating = (float) movieJsonObject.getDouble(TMDB_MOVIE_USER_RATING);

        return Movie.of(id, title, description, posterPath, releaseDate, userRating);
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPosterPath() {
        return TMDB_IMAGE_PATH + mPosterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public float getUserRating() {
        return mUserRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mPosterPath);
        dest.writeString(mReleaseDate);
        dest.writeFloat(mUserRating);
    }
}
