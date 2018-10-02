package com.android.example.popularmovies.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.example.popularmovies.data.db.MovieContract.MovieEntry;
import com.android.example.popularmovies.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private final Context context;

    public MovieRepository(Context context) {
        this.context = context;
    }

    public Uri save(@NonNull Movie movie) {
        ContentValues contentValues = getContentValues(movie);
        return context.getContentResolver()
                .insert(MovieEntry.CONTENT_URI, contentValues);
    }

    @NonNull
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        Cursor cursor = context.getContentResolver()
                .query(MovieEntry.CONTENT_URI, null, null, null, null);
        if (null != cursor) {
            while (cursor.moveToNext())
                movies.add(getMovie(cursor));
            cursor.close();
        }
        return movies;
    }

    @Nullable
    public Movie findById(@NonNull String movieId) {
        Uri uri = Uri.withAppendedPath(MovieEntry.CONTENT_URI, movieId);
        String selection = MovieEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {movieId};
        Cursor cursor = context.getContentResolver()
                .query(uri, null, selection, selectionArgs, null);
        Movie movie = null;
        if (cursor != null && cursor.moveToNext()) {
            movie = getMovie(cursor);
            cursor.close();
        }
        return movie;
    }

    public int delete(@NonNull String movieId) {
        Uri uri = Uri.withAppendedPath(MovieEntry.CONTENT_URI, movieId);
        String selection = MovieEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {movieId};
        return context.getContentResolver()
                .delete(uri, selection, selectionArgs);
    }

    @NonNull
    private ContentValues getContentValues(@NonNull Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.COLUMN_ID, movie.getId());
        contentValues.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieEntry.COLUMN_DESCRIPTION, movie.getDescription());
        contentValues.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieEntry.COLUMN_USER_RATING, movie.getRating());
        contentValues.put(MovieEntry.COLUMN_DURATION, movie.getLength());
        return contentValues;
    }

    @NonNull
    private Movie getMovie(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_ID));
        String title = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE));
        String description = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_DESCRIPTION));
        String posterPath = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH));
        String releaseDate = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE));
        String userRating = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_USER_RATING));
        String duration = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_DURATION));
        Movie movie = Movie.of(id, title, description, posterPath, releaseDate, userRating);
        movie.setLength(duration);
        return movie;
    }
}
