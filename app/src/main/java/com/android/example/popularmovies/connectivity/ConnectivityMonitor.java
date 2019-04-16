package com.android.example.popularmovies.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class ConnectivityMonitor {

    @NonNull
    protected final ConnectivityManager connectivityManager;

    @Nullable
    protected CallbackFunction<Boolean> callbackFunction;

    protected ConnectivityMonitor(@NonNull ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @NonNull
    public static ConnectivityMonitor getInstance(@NonNull Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return new NougatConnectivityMonitor(connectivityManager);
        else
            return new LegacyConnectivityMonitor(context, connectivityManager);
    }

    public abstract void startListening(@NonNull CallbackFunction<Boolean> callback);

    public abstract void stopListening();

}