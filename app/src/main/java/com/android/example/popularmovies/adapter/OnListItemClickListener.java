package com.android.example.popularmovies.adapter;

import android.support.annotation.NonNull;

@FunctionalInterface
public interface OnListItemClickListener<T> {
    void onClick(@NonNull T item);
}
