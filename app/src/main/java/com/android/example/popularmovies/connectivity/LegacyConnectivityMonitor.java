package com.android.example.popularmovies.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

public class LegacyConnectivityMonitor extends ConnectivityMonitor {

    private final Context context;
    private final IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (callbackFunction != null)
                callbackFunction.callback(isNetworkConnected());
        }
    };

    public LegacyConnectivityMonitor(@NonNull Context context,
                                     @NonNull ConnectivityManager connectivityManager) {
        super(connectivityManager);
        this.context = context;
    }

    @Override
    public void startListening(@NonNull CallbackFunction<Boolean> callback) {
        callbackFunction = callback;
        callbackFunction.callback(isNetworkConnected());
        context.registerReceiver(receiver, filter);
    }

    @Override
    public void stopListening() {
        context.unregisterReceiver(receiver);
        callbackFunction = null;
    }

    private boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
