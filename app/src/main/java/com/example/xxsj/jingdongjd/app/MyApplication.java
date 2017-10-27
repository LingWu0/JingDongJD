package com.example.xxsj.jingdongjd.app;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import okhttp3.OkHttpClient;

/**
 * Created by xxsj on 2017/9/14.
 */

public class MyApplication extends Application {

    private static Context appcontext;
    private static OkHttpClient okHttpClient;
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        appcontext=this;
        UMShareAPI.get(this);
        okHttpClient = new OkHttpClient();
    }

    public static Context context(){
        return  appcontext;
    }
    public static OkHttpClient okHttpClient(){
        return okHttpClient;
    }
}
