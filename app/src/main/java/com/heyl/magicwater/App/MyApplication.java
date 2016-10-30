package com.heyl.magicwater.App;

import android.app.Application;

/**
 * Created by heyl on 2016/10/24.
 */
public class MyApplication extends Application{

    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }



}
