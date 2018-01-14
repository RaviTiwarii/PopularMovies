package com.android.example.popularmovies.data.model;

public abstract class MovieType {
    public static final String POPULAR = "movie_type_popular";
    public static final String TOP_RATED = "movie_type_top_rated";
    public static final String FAVORITE = "movie_type_favorite";

    private MovieType() {}
}
