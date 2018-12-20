package com.coop.android;
/**
 * 基本配置文件
 */
public class AppConfigs {
    public static final boolean APP_DEBUG = BuildConfig.APP_DEBUG;
    public static final String APP_BASE_URL;
    static {
        // 判断是不是测试环境
        if (APP_DEBUG) {
            APP_BASE_URL = BuildConfig.APP_BASE_URL;
        }else{
            APP_BASE_URL = BuildConfig.APP_BASE_URL;
        }
    }
}
