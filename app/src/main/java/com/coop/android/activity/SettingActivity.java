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
import com.coop.android.utils.SharedPreferencesUtils;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class SettingActivity extends CBaseActivity implements View.OnClickListener, AlertDialog.OnDialogButtonClickListener {
    private static final String TAG = "SettingActivity";
    protected Toolbar toolBar;
    private LinearLayout logoutLL, chooseUsesLL, aboutUsLL, userIdenLL;
    private TextView chooseUsesTV;
    private String roleType;

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
        initView();
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
        logoutLL = findViewById(R.id.logoutLL);
        chooseUsesLL = findViewById(R.id.chooseUsesLL);
        chooseUsesTV = findViewById(R.id.chooseUsesTV);
    }

    @Override
    public void initData() {
        if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            roleType = ConstantUtil.PARTNERIDEN;
            chooseUsesTV.setText(getResources().getString(R.string.txt_setting_choose_topartner));
        } else {
            roleType = ConstantUtil.ENTERIDEN;
            chooseUsesTV.setText(getResources().getString(R.string.txt_setting_choose_toenter));
        }
    }

    @Override
    public void initEvent() {
        userIdenLL.setOnClickListener(this);
        aboutUsLL.setOnClickListener(this);
        logoutLL.setOnClickListener(this);
        chooseUsesLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userIdenLL:
                toActivity(UserIdenActivity.createIntent(SettingActivity.this));
                break;
            case R.id.aboutUsLL:
                toActivity(AboutUsActivity.createIntent(SettingActivity.this));
                break;
            case R.id.logoutLL:
                new AlertDialog(mContext, "提示", "是否退出？", true, 0, this).show();
                break;
            case R.id.chooseUsesLL:
                changeRole(roleType);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (isPositive) {
            SharedPreferencesUtils.setUserInfo(mContext, "");   //清空用户信息
            //登出统计
            MobclickAgent.onProfileSignOff();
            startActivity(LoginActivity.createIntent(this, false));
            finish();
        }
    }

    private void changeRole(String roleType) {
        HttpPostApi postEntity = new HttpPostApi(changeRoleOnNextListener, this, HttpPostApi.CHANGE_ROLE, true);
        postEntity.setRoleType(roleType);
        postEntity.setUserId(UserConfigs.getInstance().getId());
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    HttpOnNextListener changeRoleOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            if (code == 501 || code == 502) {
                startActivity(LoginChooseActivity.createIntent(mContext));
                return;
            }
            String userInfo = SharedPreferencesUtils.getUserInfo(mContext);
            LoginResponseBean user = JSON.parseObject(userInfo, LoginResponseBean.class);
            UserInfo userInfoTemp = user.getUser();
            userInfoTemp.setLastLoginRole(roleType);
            user.setUser(userInfoTemp);
            userInfo = JSON.toJSONString(user);
            SharedPreferencesUtils.setUserInfo(mContext, userInfo);
            UserConfigs.loadUserInfo(userInfo);
//            ToastUtil.showShortToast(getApplicationContext(), "角色切换成功!");
            HomeActivity.newInstance(mContext, true);
            finish();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(getApplicationContext(), e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        initData();
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
