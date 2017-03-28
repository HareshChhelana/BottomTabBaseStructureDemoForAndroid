package com.bottomtabbasestructuredemo;

import android.app.Application;

import com.bottomtabbasestructuredemo.utilities.ActivityLifecycle;


public class BottomTabApplication extends Application {

    public static int tabPosition;

    private static BottomTabApplication instance;

    public static BottomTabApplication getInstance() {
        return instance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifecycle.init(this);
    }

}
