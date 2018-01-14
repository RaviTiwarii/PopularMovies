package com.android.example.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.android.example.popularmovies.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesDb.db";
    private static final int VERSION = 1;
    private static final String CREATE_TABLE_MOVIE   = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
            MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieEntry.COLUMN_ID + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_USER_RATING + " TEXT NOT NULL," +
            MovieEntry.COLUMN_DURATION + " TEXT NOT NULL" + ");";

    // Write Update Query On Database Version Update
    private static final String ALTER_TABLE_MOVIE_1 = "";
    private static final String ALTER_TABLE_MOVIE_2 = "";

    public MovieDbHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Execute database alter code for database version 2
            db.execSQL(ALTER_TABLE_MOVIE_1);
        } else if (oldVersion < 3) {
            // Execute database alter code for database version 3
            db.execSQL(ALTER_TABLE_MOVIE_2);
        }
    }
}
