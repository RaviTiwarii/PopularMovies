package com.android.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is a model class for movie
 * @author Ravi Tiwari
 */
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

    private long mId;
    private String mTitle = "";
    private String mDescription = "";
    private String mPosterPath = "";
    private String mReleaseDate = "";
    private float mUserRating;

    /**
     * Create a new movie object with movie id
     * @param id id of the movie
     */
    public Movie(long id) {
        mId = id;
    }

    /**
     * Create a new movie object by reading values from parcel object
     * @param in parcel from which values should be read.
     */
    public Movie(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mDescription = in.readString();
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mUserRating = in.readFloat();
    }

    /**
     * Crate a new movie object from the json data.
     * @param movieJsonObject json object containing movie details.
     * @return movie object
     * @throws JSONException if there is any parsing error.
     */
    @NonNull
    public static Movie fromJson(JSONObject movieJsonObject) throws JSONException {
        long id = movieJsonObject.getLong(TMDB_MOVIE_ID);
        String title = movieJsonObject.getString(TMDB_MOVIE_TITLE);
        String description = movieJsonObject.getString(TMDB_MOVIE_DESCRIPTION);
        String posterPath = movieJsonObject.getString(TMDB_MOVIE_POSTER_PATH);
        String releaseDate = movieJsonObject.getString(TMDB_MOVIE_RELEASE_DATE);
        float userRating = (float) movieJsonObject.getDouble(TMDB_MOVIE_USER_RATING);

        Movie movie = new Movie(id);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setPosterPath(posterPath);
        movie.setReleaseDate(releaseDate);
        movie.setUserRating(userRating);

        return movie;
    }

    //----------------------------------
    // Getters and Setters
    //----------------------------------
    public long getmId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPosterPath() {
        return TMDB_IMAGE_PATH + mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public float getUserRating() {
        return mUserRating;
    }

    public void setUserRating(float userRating) {
        mUserRating = userRating;
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
