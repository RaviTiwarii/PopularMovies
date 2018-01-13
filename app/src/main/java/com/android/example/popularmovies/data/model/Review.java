package com.android.example.popularmovies.data.model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Review {
    private static final String KEY_ID = "id";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    private final String id;
    private final String author;
    private final String content;

    private Review(@NonNull String id, @NonNull String author, @NonNull String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public static Review of(@NonNull String id, @NonNull String author, @NonNull String content) {
        return new Review(id, author, content);
    }

    public static Review from(@NonNull JSONObject reviewJson) throws JSONException {
        String id = reviewJson.getString(KEY_ID);
        String author = reviewJson.getString(KEY_AUTHOR);
        String content = reviewJson.getString(KEY_CONTENT);
        return new Review(id, author, content);
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
