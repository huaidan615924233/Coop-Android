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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.LoginResponseBean;
import com.coop.android.model.UserInfo;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.DataCleanManager;
import com.coop.android.utils.SharedPreferencesUtils;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.statusbar.StatusBarCompat;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class SettingActivity extends CBaseActivity implements View.OnClickListener, AlertDialog.OnDialogButtonClickListener {
    private static final String TAG = "SettingActivity";
    protected Toolbar toolBar;
    private CardView logoutCV;
    private LinearLayout aboutUsLL, userIdenLL, updatePwdLL, cleanCacheLL,updatePwdPLL;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        StatusBarUtils.setStatusBarColorDefault(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_setting));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userIdenLL = findViewById(R.id.userIdenLL);
        aboutUsLL = findViewById(R.id.aboutUsLL);
        updatePwdLL = findViewById(R.id.updatePwdLL);
        cleanCacheLL = findViewById(R.id.cleanCacheLL);
        logoutCV = findViewById(R.id.logoutCV);
        updatePwdPLL = findViewById(R.id.updatePwdPLL);
    }

    @Override
    public void initData() {
        if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())){
            updatePwdPLL.setVisibility(View.VISIBLE);
        }else
            updatePwdPLL.setVisibility(View.GONE);
    }

    @Override
    public void initEvent() {
        userIdenLL.setOnClickListener(this);
        aboutUsLL.setOnClickListener(this);
        logoutCV.setOnClickListener(this);
        updatePwdLL.setOnClickListener(this);
        cleanCacheLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userIdenLL:
                toActivity(UserSafeActivity.createIntent(mContext));
                break;
            case R.id.aboutUsLL:
                toActivity(AboutUsActivity.createIntent(mContext));
                break;
            case R.id.logoutCV:
                new AlertDialog(mContext, "确定要退出当前账户?", "", true, "确认退出", ConstantUtil.LOGOUT, this).show();
                break;
            case R.id.updatePwdLL:
                toActivity(UpdatePasswordActivity.createIntent(mContext, ConstantUtil.SETPASSWORD));
                break;
            case R.id.cleanCacheLL:
                new AlertDialog(mContext, "提示", "是否清除缓存？", true, ConstantUtil.LOGOUT, this).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        switch (requestCode) {
            case ConstantUtil.LOGOUT:
                if (isPositive) {
                    SharedPreferencesUtils.setUserInfo(mContext, "");   //清空用户信息
                    //登出统计
                    MobclickAgent.onProfileSignOff();
                    startActivity(LoginActivity.createIntent(this, false));
                    finish();
                }
                break;
            case ConstantUtil.CLEANCACHE:
                if (isPositive) {
                    DataCleanManager.cleanApplicationData(mContext, "/coop");
                    startActivity(WelcomeActivity.createIntent(mContext));
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("设置页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("设置页面");
        MobclickAgent.onPause(this);
    }
}
