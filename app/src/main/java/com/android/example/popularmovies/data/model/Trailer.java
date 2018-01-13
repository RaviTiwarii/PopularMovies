package com.android.example.popularmovies.data.model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Trailer {
    private static final String KEY_NAME = "name";
    private static final String KEY_KEY = "key";

    private final String name;
    private final String key;

    private Trailer(@NonNull String name, @NonNull String key) {
        this.name = name;
        this.key = key;
    }

    public static Trailer of(@NonNull String name, @NonNull String key) {
        return new Trailer(name, key);
    }

    public static Trailer from(@NonNull JSONObject jsonTrailer) throws JSONException {
        String name = jsonTrailer.getString(KEY_NAME);
        String key = jsonTrailer.getString(KEY_KEY);
        return new Trailer(name, key);
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
