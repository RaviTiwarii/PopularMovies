package com.android.example.popularmovies.data.mapper;

import android.support.annotation.Nullable;

import com.android.example.popularmovies.data.model.Review;

import org.json.JSONException;
import org.json.JSONObject;

public class ReviewJsonMapper implements JsonMapper<Review> {

    private static final String KEY_ID = "id";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    @Override
    public String toJson(Review type) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    public Review fromJson(JSONObject reviewJson) throws JSONException {
        String id = reviewJson.getString(KEY_ID);
        String author = reviewJson.getString(KEY_AUTHOR);
        String content = reviewJson.getString(KEY_CONTENT);
        return Review.of(id, author, content);
    }
}
