package com.android.example.popularmovies.ui.adapter;

import android.support.annotation.NonNull;

@FunctionalInterface
public interface OnListItemClickListener<T> {

    void onClick(@NonNull T item);
}
