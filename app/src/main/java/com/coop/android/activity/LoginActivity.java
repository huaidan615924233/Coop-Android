package com.coop.android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.coop.android.R;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.LoginResponseBean;
import com.coop.android.utils.ToastUtil;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener, AlertDialog.OnDialogButtonClickListener {
    private static final String TAG = "LoginActivity";
    private int countSeconds = 60;//倒计时秒数
    private EditText phoneEV, passwordEV;
    private TextView getVerifyEV, serveDescTV;
    private Button loginBtn;
    private CheckBox selectCB;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @SuppressLint("HandlerLeak")
    private Handler mCountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0) {
                --countSeconds;
                getVerifyEV.setTextColor(getResources().getColor(R.color.color_969696));
                getVerifyEV.setText("(" + countSeconds + ")后获取验证码");
                mCountHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                countSeconds = 60;
                getVerifyEV.setTextColor(getResources().getColor(R.color.color_58ACFF));
                getVerifyEV.setText("请重新获取验证码");
            }
        }
    };
    private String userinfomsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
        initData();
    }

    @Override
    public void initView() {
        phoneEV = findViewById(R.id.phoneEV);
        getVerifyEV = findViewById(R.id.getVerifyEV);
        passwordEV = findViewById(R.id.passwordEV);
        loginBtn = findViewById(R.id.loginBtn);
        serveDescTV = findView(R.id.serveDescTV);
        selectCB = findViewById(R.id.selectCB);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        getVerifyEV.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getVerifyEV:
                if (countSeconds == 60) {
                    String mobile = phoneEV.getText().toString();
                    Log.e(TAG, "mobile==" + mobile);
                    getVerify(mobile);
                } else {
                    ToastUtil.showShortToast(getApplicationContext(), "不能重复发送验证码");
                }
                break;
            case R.id.loginBtn:
                login();
                break;
            default:
                break;
        }
    }

    //获取信息进行登录
    public void login() {
        String mobile = phoneEV.getText().toString().trim();
        String verifyCode = passwordEV.getText().toString().trim();
        if ("".equals(mobile)) {
            Log.e(TAG, "mobile=" + mobile);
            new AlertDialog(mContext, "提示", "手机号码不能为空", true, 0, this).show();
        } else if (!StringUtil.isPhone(mobile)) {
            new AlertDialog(mContext, "提示", "请输入正确的手机号码", true, 0, this).show();
        } else if (StringUtil.isEmpty(verifyCode)) {
            new AlertDialog(mContext, "提示", "请输入验证码", true, 0, this).show();
        } else {
            Log.e(TAG, "输入了正确的手机号");
            if (!selectCB.isChecked()) {
                ToastUtil.showShortToast(getApplicationContext(), "请同意服务协议!");
                return;
            }
            HttpPostApi postEntity = new HttpPostApi(loginOnNextListener, this, HttpPostApi.LOGIN_URL, true);
            postEntity.setPhoneNumber(mobile);
            postEntity.setCode(verifyCode);
            HttpManager manager = HttpManager.getInstance();
            manager.doHttpDeal(postEntity);
        }
    }

    //获取验证码信息，判断是否有手机号码
    private void getVerify(String mobile) {
        if ("".equals(mobile)) {
            Log.e(TAG, "mobile=" + mobile);
            new AlertDialog(mContext, "提示", "手机号码不能为空", true, 0, this).show();
        } else if (!StringUtil.isPhone(mobile)) {
            new AlertDialog(mContext, "提示", "请输入正确的手机号码", true, 0, this).show();
        } else {
            Log.e(TAG, "输入了正确的手机号");
            requestVerifyCode(mobile);
        }
    }

    //获取验证码信息，进行验证码请求
    private void requestVerifyCode(String mobile) {
        HttpPostApi postEntity = new HttpPostApi(verifyOnNextListener, this, HttpPostApi.GET_VERIFY, true);
        postEntity.setPhoneNumber(mobile);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
        startCountBack();
    }

    //获取验证码信息,进行计时操作
    private void startCountBack() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getVerifyEV.setText(countSeconds + "");
                mCountHandler.sendEmptyMessage(0);
            }
        });
    }


    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {

    }

    HttpOnNextListener loginOnNextListener = new HttpOnNextListener<LoginResponseBean>() {

        @Override
        public void onNext(LoginResponseBean loginResponseBean) {
            ToastUtil.showShortToast(getApplicationContext(), "接口请求成功！" + loginResponseBean.toString());
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(getApplicationContext(), getResources().getString(R.string.txt_server_error));
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };
    HttpOnNextListener verifyOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string) {
            ToastUtil.showShortToast(getApplicationContext(), "验证码已发送!");
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(getApplicationContext(), getResources().getString(R.string.txt_server_error));
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };
}