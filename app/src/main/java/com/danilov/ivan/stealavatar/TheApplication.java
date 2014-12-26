package com.danilov.ivan.stealavatar;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.danilov.ivan.stealavatar.view.activity.MainActivity;

public class TheApplication extends Application {

    private static TheApplication sApplication;

    private SharedPreferences sharedPreferences;


    public static TheApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public SharedPreferences getPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        }
        return sharedPreferences;
    }

    /**
     * start main activity and finish current
     *
     * @param bundle
     */
    public static void startMainActivity(Bundle bundle, Activity activity) {
        Log.d("START MAIN", "start");
        Intent intent = new Intent(activity, MainActivity.class);
        if (bundle != null) intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startMainActivity(Activity activity) {
        startMainActivity(null, activity);
    }
}

