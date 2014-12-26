package com.danilov.ivan.stealavatar.view.fragment;

import android.os.Handler;
import android.view.View;

import com.danilov.ivan.stealavatar.R;
import com.danilov.ivan.stealavatar.TheApplication;
import com.danilov.ivan.stealavatar.annotation.Layout;

@Layout(R.layout.fragm_splash)
public class SplashFragment extends AbstractFragment {

    private final static int SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void initView(View view) {
        startMainActivityAfterPause();
    }

    private void startMainActivityAfterPause() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                TheApplication.startMainActivity(getActivity());
            }
        }, SPLASH_SCREEN_DELAY);
    }
}
