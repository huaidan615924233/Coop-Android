//
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \|     |// '.
//                 / \|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \  -  /// |     |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG
package com.coop.android;

import android.app.Application;

import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;
import retrofit_rx.RxRetrofitApp;

/**
 * Created by aaron on 16/9/7.
 */

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        ZXingLibrary.initDisplayOpinion(this);
        RxRetrofitApp.init(this, AppConfigs.APP_DEBUG);
        JPushInterface.setDebugMode(AppConfigs.APP_DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        MobSDK.init(this);          //初始化ShareSDK
    }
}
