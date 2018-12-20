package com.coop.android;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import retrofit_rx.RxRetrofitApp;

/**
 * Created by aaron on 16/9/7.
 */

public class MApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        RxRetrofitApp.init(this,AppConfigs.APP_DEBUG);
    }
}
