package com.danilov.ivan.stealavatar.network;

public interface INetworkConnectivityStateCallback {

    enum NetworkTypes {
        DISABLE,
        MOBILE,
        WIFI,
        WIMAX
    }

    void OnNetworkStateChange(NetworkTypes type);
}
