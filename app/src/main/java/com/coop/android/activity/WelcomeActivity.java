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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.adapter.PartnerAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.AppCommonBean;
import com.coop.android.model.CoopPartnerListResponseBean;
import com.coop.android.utils.AppStatus;
import com.coop.android.utils.AppStatusManager;
import com.coop.android.utils.SharedPreferencesUtils;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.Log;


/**
 * 启动页面
 * 在获取用户信息之后才会进入下一个页面
 */
public class WelcomeActivity extends Activity implements AlertDialog.OnDialogButtonClickListener {
    private static final String TAG = "WelcomeActivity";
    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    private Context mContext;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        MobclickAgent.openActivityDurationTrack(false);
        initData();
    }

    private void initData() {
        HttpPostApi postEntity = new HttpPostApi(appVersionOnNextListener, WelcomeActivity.this, HttpPostApi.APP_VERSION, true);
        postEntity.setUserId(UserConfigs.getInstance().getId());
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    HttpOnNextListener appVersionOnNextListener = new HttpOnNextListener<AppCommonBean>() {

        @Override
        public void onNext(AppCommonBean appCommonBean, int code) {
            if (appCommonBean.isIs_update())
                if (appCommonBean.isForce_update()) {
                    AlertDialog alertDialog = new AlertDialog(mContext, "提示", "当前应用不是最新版本\n请下载最新版本再使用！",
                            false, 0, WelcomeActivity.this);
                    alertDialog.show();
                    alertDialog.setCancelable(false);
                } else
                    enterHomeActivity();
            else
                enterHomeActivity();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext, e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

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
        //app状态改为正常
        AppStatusManager.getInstance().setAppStatus(AppStatus.STATUS_NORMAL);
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
            else if (TextUtils.isEmpty(UserConfigs.getInstance().getCardNo()))
                startActivity(LoginIdenActivity.createIntent(this));
            else
                HomeActivity.newInstance(this);
            finish();
        }
    }

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        SharedPreferencesUtils.setUserInfo(mContext, "");   //清空用户信息
        System.exit(0);
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