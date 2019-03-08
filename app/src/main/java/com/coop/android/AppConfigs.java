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
/**
 * 基本配置文件
 */
public class AppConfigs {
    public static final boolean APP_DEBUG = BuildConfig.APP_DEBUG;
    public static final String APP_BASE_URL;
    public static final String APP_BASE_FILE_URL;
    static {
        // 判断是不是测试环境
        if (APP_DEBUG) {
            APP_BASE_URL = BuildConfig.APP_BASE_URL;
            APP_BASE_FILE_URL = BuildConfig.APP_BASE_FILE_URL;
        }else{
            APP_BASE_URL = BuildConfig.APP_BASE_URL;
            APP_BASE_FILE_URL = BuildConfig.APP_BASE_FILE_URL;
        }
    }
}
