package com.danilov.ivan.stealavatar.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Utils {

    public static WindowManager.LayoutParams dialogSize(Dialog dialog, Activity activity) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        double x;
        //width dialog window is 60% of the screen width
        if (android.os.Build.VERSION.SDK_INT > 13) {
            x = point.x * 0.6;
        } else {
            x = display.getWidth() * 0.6;
        }
        display.getSize(point);
        lp.width = (int) x;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return lp;
    }

}
