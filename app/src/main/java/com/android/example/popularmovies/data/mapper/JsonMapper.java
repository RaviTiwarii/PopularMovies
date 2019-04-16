package com.android.example.popularmovies.data.mapper;

import org.json.JSONException;
import org.json.JSONObject;

interface JsonMapper<T> {
    String toJson(T type);
    T fromJson(JSONObject jsonObject) throws JSONException;
}
