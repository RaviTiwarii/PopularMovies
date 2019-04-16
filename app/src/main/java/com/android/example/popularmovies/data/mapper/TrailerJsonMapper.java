package com.android.example.popularmovies.data.mapper;

import com.android.example.popularmovies.data.model.Trailer;

import org.json.JSONException;
import org.json.JSONObject;

public class TrailerJsonMapper implements JsonMapper<Trailer> {
    private static final String KEY_NAME = "name";
    private static final String KEY_KEY = "key";

    @Override
    public String toJson(Trailer type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Trailer fromJson(JSONObject jsonTrailer) throws JSONException {
        String name = jsonTrailer.getString(KEY_NAME);
        String key = jsonTrailer.getString(KEY_KEY);
        return Trailer.of(name, key);
    }
}
