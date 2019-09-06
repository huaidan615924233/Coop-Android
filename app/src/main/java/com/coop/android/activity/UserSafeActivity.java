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
import com.coop.android.utils.SharedPreferencesUtils;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class UserSafeActivity extends CBaseActivity implements View.OnClickListener {
    private static final String TAG = "UserSafeActivity";
    protected Toolbar toolBar;
    private LinearLayout updatePhoneNumberLL, userIdenLL;
    private TextView phoneNumberTV;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, UserSafeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_safe);
        StatusBarUtils.setStatusBarColorDefault(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_usersafe));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userIdenLL = findViewById(R.id.userIdenLL);
        updatePhoneNumberLL = findViewById(R.id.updatePhoneNumberLL);
        phoneNumberTV = findViewById(R.id.phoneNumberTV);
    }

    @Override
    public void initData() {
        phoneNumberTV.setText(
                StringUtil.isEmail(UserConfigs.getInstance().getMobilePhone()) ? "" : UserConfigs.getInstance().getMobilePhone());
    }

    @Override
    public void initEvent() {
        userIdenLL.setOnClickListener(this);
        updatePhoneNumberLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userIdenLL:
                toActivity(UserIdenActivity.createIntent(mContext));
                break;
            case R.id.updatePhoneNumberLL:
                toActivity(UpdatePhoneNumberActivity.createIntent(mContext));
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("账户与安全页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("账户与安全页面");
        MobclickAgent.onPause(this);
    }
}
