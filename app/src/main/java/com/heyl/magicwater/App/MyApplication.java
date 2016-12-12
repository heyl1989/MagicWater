package com.heyl.magicwater.App;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.bmob.v3.Bmob;

/**
 * Created by heyl on 2016/10/24.
 */
public class MyApplication extends Application{

    {

        PlatformConfig.setWeixin("wx52dca3261e63bc46", "6bed365b25b5acc3fe569ff126782b4a");
        PlatformConfig.setSinaWeibo("245569765", "3e818adcb30c721b0585a2d963fef928");
        PlatformConfig.setQQZone("1105744403", "km6wdTndtd27sQAR");


    }

    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        UMShareAPI.get(this);
        Bmob.initialize(this, "c91f32b096ee7ec886db941293700999");
    }



}
