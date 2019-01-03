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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
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
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class LoginChooseActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "LoginChooseActivity";
    private EditText userNameET;
    private Button usesOneBtn, usesTwoBtn;
    private String roleType;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginChooseActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choose);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        userNameET = findViewById(R.id.userNameET);
        usesOneBtn = findViewById(R.id.usesOneBtn);
        usesTwoBtn = findViewById(R.id.usesTwoBtn);
    }

    @Override
    public void initData() {
        if (StringUtil.isEmpty(UserConfigs.getInstance().getLastLoginRole())) {
            userNameET.setFocusableInTouchMode(true);
            userNameET.setFocusable(true);
            userNameET.requestFocus();
            usesOneBtn.setEnabled(true);
            usesTwoBtn.setEnabled(true);
            usesOneBtn.setBackgroundResource(R.drawable.btn_background_border);
            usesTwoBtn.setBackgroundResource(R.drawable.btn_background_border);
        } else if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            userNameET.setFocusableInTouchMode(false);
            userNameET.setFocusable(false);
            userNameET.setText(UserConfigs.getInstance().getNickName());
            usesOneBtn.setEnabled(false);
            usesTwoBtn.setEnabled(true);
            usesOneBtn.setBackgroundResource(R.drawable.btn_background_unenable_border);
            usesTwoBtn.setBackgroundResource(R.drawable.btn_background_border);
        } else {
            userNameET.setFocusableInTouchMode(false);
            userNameET.setFocusable(false);
            userNameET.setText(UserConfigs.getInstance().getNickName());
            usesOneBtn.setEnabled(true);
            usesTwoBtn.setEnabled(false);
            usesOneBtn.setBackgroundResource(R.drawable.btn_background_border);
            usesTwoBtn.setBackgroundResource(R.drawable.btn_background_unenable_border);
        }
    }

    @Override
    public void initEvent() {
        usesOneBtn.setOnClickListener(this);
        usesTwoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usesOneBtn:
                roleType = ConstantUtil.ENTERIDEN;
//                if (StringUtil.isEmpty(UserConfigs.getInstance().getLastLoginRole()))
                addRole(roleType);
//                else
//                    changeRole(roleType);
                break;
            case R.id.usesTwoBtn:
                roleType = ConstantUtil.PARTNERIDEN;
//                if (StringUtil.isEmpty(UserConfigs.getInstance().getLastLoginRole()))
                addRole(roleType);
//                else
//                    changeRole(roleType);
                break;
            default:
                break;
        }
    }

    private void addRole(String roleType) {
        if (StringUtil.isEmpty(userNameET.getText().toString())) {
            ToastUtil.showShortToast(mContext, "请输入用户昵称!");
            return;
        }
        HttpPostApi postEntity = new HttpPostApi(addRoleOnNextListener, this, HttpPostApi.ADD_ROLE, true);
        postEntity.setNickName(userNameET.getText().toString());
        postEntity.setRoleType(roleType);
        postEntity.setUserId(UserConfigs.getInstance().getId());
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    HttpOnNextListener addRoleOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "Token失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            String userInfo = SharedPreferencesUtils.getUserInfo(mContext);
            LoginResponseBean user = JSON.parseObject(userInfo, LoginResponseBean.class);
            UserInfo userInfoTemp = user.getUser();
            userInfoTemp.setLastLoginRole(roleType);
            userInfoTemp.setNickName(userNameET.getText().toString());
            user.setUser(userInfoTemp);
            userInfo = JSON.toJSONString(user);
            SharedPreferencesUtils.setUserInfo(mContext, userInfo);
            UserConfigs.loadUserInfo(userInfo);
//            ToastUtil.showShortToast(getApplicationContext(), "角色创建成功!");
            HomeActivity.newInstance(mContext, false);
            finish();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(getApplicationContext(), getResources().getString(R.string.txt_server_error));
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("选择角色页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("选择角色页面");
        MobclickAgent.onPause(this);
    }
}
