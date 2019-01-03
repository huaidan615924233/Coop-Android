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
package com.coop.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.utils.SharedPreferencesUtils;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;


/**
 * 启动页面
 * 在获取用户信息之后才会进入下一个页面
 */
public class WelcomeActivity extends Activity {
    private static final String TAG = "WelcomeActivity";
    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.openActivityDurationTrack(false);
        enterHomeActivity();
    }

    /**
     * @return true 首次启动，false 非首次启动
     */
    private boolean isFirstRun() {
        return SharedPreferencesUtils.getFirstRun(this);
    }

    ;

    /**
     * 进入到启动页面
     */
    private void enterHomeActivity() {
        if (isFirstRun()) {
            GuideActivity.newInstance(this);
            finish();
        } else if (!SharedPreferencesUtils.hasUserInfo(this)) {
            startActivity(LoginActivity.createIntent(this, false));
            finish();
        } else {
            String userInfo = SharedPreferencesUtils.getUserInfo(this);
            UserConfigs.loadUserInfo(userInfo);
            if (TextUtils.isEmpty(UserConfigs.getInstance().getLastLoginRole()))
                startActivity(LoginChooseActivity.createIntent(this));
            else
                HomeActivity.newInstance(this,false);
            finish();
        }
    }

    /**
     * 双击返回键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtil.showShortToast(WelcomeActivity.this, "再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("加载页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("加载页面");
        MobclickAgent.onPause(this);
    }
}