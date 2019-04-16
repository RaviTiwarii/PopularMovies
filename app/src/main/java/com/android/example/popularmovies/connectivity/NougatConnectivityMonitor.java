package com.android.example.popularmovies.connectivity;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.support.annotation.NonNull;

@TargetApi(Build.VERSION_CODES.N)
public class NougatConnectivityMonitor extends ConnectivityMonitor {

    @NonNull
    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            if (callbackFunction != null)
                callbackFunction.callback(true);
        }

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            if (callbackFunction != null)
                callbackFunction.callback(false);
        }
    };

    public NougatConnectivityMonitor(@NonNull ConnectivityManager connectivityManager) {
        super(connectivityManager);
    }

    @Override
    public void startListening(@NonNull CallbackFunction<Boolean> callback) {
        callbackFunction = callback;
        callbackFunction.callback(false);
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    public void stopListening() {
        connectivityManager.unregisterNetworkCallback(networkCallback);
        callbackFunction = null;
    }
}
