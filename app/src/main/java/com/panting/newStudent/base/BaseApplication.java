package com.panting.newStudent.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


/**
 * baseApplication
 */
public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Handler getAppHandler() {
        Looper.prepare();
        return new Handler();
    }

    public static Context getContext() {
        return mContext;
    }
}
