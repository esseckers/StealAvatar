package com.danilov.ivan.stealavatar.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.danilov.ivan.stealavatar.TheApplication;

import java.util.ArrayList;
import java.util.List;

public class NetworkConnectivityListener {
    private static final String TAG = "NetworkConnectivityListener";
    private static NetworkConnectivityListener instance;
    private static Context mContext;
    private static boolean mListening;
    private static List<INetworkConnectivityStateCallback> networkConnectivityStateCallbackList = new ArrayList<INetworkConnectivityStateCallback>();
    private ConnectivityBroadcastReceiver mReceiver;

    NetworkConnectivityListener() {
        mReceiver = new ConnectivityBroadcastReceiver();
    }

    public static NetworkConnectivityListener getInstance() {
        if (instance == null) {
            instance = new NetworkConnectivityListener();
        }
        return instance;
    }

    /**
     * Inform all instances of {@link INetworkConnectivityStateCallback} what network was enabled with {@link INetworkConnectivityStateCallback.NetworkTypes} type
     *
     * @param networkType Network connection type
     */
    private static void invokeAllCallbacks(INetworkConnectivityStateCallback.NetworkTypes networkType) {
        for (INetworkConnectivityStateCallback callback : networkConnectivityStateCallbackList) {
            if (callback != null) callback.OnNetworkStateChange(networkType);
        }
    }

    /**
     * Start listening
     */
    public synchronized void startListening() {
        if (!mListening) {
            mContext = TheApplication.getInstance().getApplicationContext();

            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            mContext.registerReceiver(mReceiver, filter);
            mListening = true;
        }
    }

    /**
     * Stop listening
     */
    public synchronized void stopListening() {
        if (mListening) {
            mContext.unregisterReceiver(mReceiver);
            mContext = null;
            mListening = false;
        }
    }

    public void addNetworkConnectivityListener(INetworkConnectivityStateCallback callback) {
        if (networkConnectivityStateCallbackList.contains(callback)) {
            Log.d(TAG, "Callback already registered");
        } else {
            networkConnectivityStateCallbackList.add(callback);
        }
    }

    public void removeNetworkConnectivityListener(INetworkConnectivityStateCallback callback) {
        if (!networkConnectivityStateCallbackList.contains(callback)) {
            Log.d(TAG, "Callback already unregistered");
        } else {
            networkConnectivityStateCallbackList.remove(callback);
        }
    }

    public static class ConnectivityBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION) || !mListening) {
                return;
            }
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (noConnectivity) {
                invokeAllCallbacks(INetworkConnectivityStateCallback.NetworkTypes.DISABLE);
                return;
            }
            // Check each connection type
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            /**
             * WIFI
             */
            NetworkInfo network = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (isNetworkAvailable(network)) {
                invokeAllCallbacks(INetworkConnectivityStateCallback.NetworkTypes.WIFI);
                return;
            }
            /**
             * 2G/3G
             */
            network = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (isNetworkAvailable(network)) {
                invokeAllCallbacks(INetworkConnectivityStateCallback.NetworkTypes.MOBILE);
                return;
            }
            /**
             * 4G
             */
            network = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
            if (isNetworkAvailable(network)) {
                invokeAllCallbacks(INetworkConnectivityStateCallback.NetworkTypes.WIMAX);
                return;
            }
            invokeAllCallbacks(INetworkConnectivityStateCallback.NetworkTypes.DISABLE);
        }

        private boolean isNetworkAvailable(NetworkInfo network) {
            return network != null && network.isAvailable() && network.isConnected();
        }
    }
}
