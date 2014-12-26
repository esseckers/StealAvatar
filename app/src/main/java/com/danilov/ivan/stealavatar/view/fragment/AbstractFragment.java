package com.danilov.ivan.stealavatar.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danilov.ivan.stealavatar.annotation.Layout;
import com.danilov.ivan.stealavatar.network.INetworkConnectivityStateCallback;
import com.danilov.ivan.stealavatar.network.NetworkConnectivityListener;
import com.danilov.ivan.stealavatar.view.dialog.IDialogOnDismissListener;
import com.danilov.ivan.stealavatar.view.dialog.WarningDialog;

import butterknife.ButterKnife;

public class AbstractFragment extends Fragment implements INetworkConnectivityStateCallback {

    private WarningDialog warningDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getViewLayout(), container, false);
        ButterKnife.inject(this, view);
        initView(view);
        return view;
    }

    protected void initView(View view) {

    }

    protected int getViewLayout() {
        Layout layout = this.getClass().getAnnotation(Layout.class);
        return layout != null ? layout.value() : 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkConnectivityListener.getInstance().addNetworkConnectivityListener(this);
        NetworkConnectivityListener.getInstance().startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        NetworkConnectivityListener.getInstance().removeNetworkConnectivityListener(this);
        NetworkConnectivityListener.getInstance().stopListening();
    }

    @Override
    public void OnNetworkStateChange(NetworkTypes type) {
        if (type == NetworkTypes.DISABLE) {
            warningDialog = new WarningDialog();
            warningDialog.setCancelable(false);
            warningDialog.setOnDismissListener(new IDialogOnDismissListener() {
                @Override
                public void onOk() {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            });
            warningDialog.show(getFragmentManager(), null);
        } else {
            if (warningDialog != null) {
                warningDialog.dismiss();
            }
        }
    }
}
