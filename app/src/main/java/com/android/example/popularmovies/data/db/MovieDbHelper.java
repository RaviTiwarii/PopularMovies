package com.android.example.popularmovies.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.android.example.popularmovies.data.db.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesDb.db";
    private static final int VERSION = 1;

    // Write Update Query On Database Version Update
    private static final String ALTER_TABLE_MOVIE_1 = "";
    private static final String ALTER_TABLE_MOVIE_2 = "";

    public MovieDbHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieEntry.getCreateQuery());
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
