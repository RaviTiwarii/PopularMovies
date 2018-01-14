package com.android.example.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class defines the contract for movie's database
 */
public final class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.android.example.popularmovies";
    public static final String PATH_MOVIES = "movies";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private MovieContract() {}

    /**
     * This class defines the schema for movie's table
     */
    public static class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_DURATION = "duration";
    }
}
