package com.coop.android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.LoginResponseBean;
import com.coop.android.model.UserInfo;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.Md5EncryptionHelper;
import com.coop.android.utils.SharedPreferencesUtils;
import com.coop.android.utils.ToastUtil;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener, AlertDialog.OnDialogButtonClickListener {
    public static final String TAG = "UpdatePasswordActivity";
    private int type;
    protected Toolbar toolBar;
    private int countSeconds = 60;//倒计时秒数
    private EditText phoneEV, passwordEV, setPassEV;
    private TextView getVerifyEV;
    private Button setPwdBtn;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, int type) {
        return new Intent(context, UpdatePasswordActivity.class).putExtra("type", type);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        intent = getIntent();
        type = intent.getIntExtra("type", ConstantUtil.UPDATEPASSWORD);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        if (type == ConstantUtil.UPDATEPASSWORD)
            tvBaseTitle.setText(getString(R.string.txt_title_update_password));
        else
            tvBaseTitle.setText(getString(R.string.txt_title_set_password));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        phoneEV = findViewById(R.id.phoneEV);
        getVerifyEV = findViewById(R.id.getVerifyEV);
        passwordEV = findViewById(R.id.passwordEV);
        setPwdBtn = findViewById(R.id.setPwdBtn);
        setPassEV = findViewById(R.id.setPassEV);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        getVerifyEV.setOnClickListener(this);
        setPwdBtn.setOnClickListener(this);
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
            case R.id.setPwdBtn:
                setPassword();
                break;
            default:
                break;
        }
    }

    public void setPassword() {
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
            if (StringUtil.isEmpty(setPassEV.getText().toString()) || setPassEV.getText().toString().length() < 6) {
                new AlertDialog(mContext, "提示", getResources().getString(R.string.txt_password_failed), true, 0, this).show();
                return;
            }
            HttpPostApi postEntity = new HttpPostApi(setPassWordOnNextListener, this, HttpPostApi.SET_PAY_PASSWORD, true);
            postEntity.setPhoneNumber(mobile);
            postEntity.setCode(verifyCode);
            postEntity.setPayPassword(Md5EncryptionHelper.getMD5WithSalt(setPassEV.getText().toString(), Md5EncryptionHelper.SALT));
            postEntity.setSalt(Md5EncryptionHelper.SALT);
            HttpManager manager = HttpManager.getInstance();
            manager.doHttpDeal(postEntity);
        }
    }

    //获取验证码信息，判断是否有手机号码
    private void getVerify(String mobile) {
        if (StringUtil.isEmpty(mobile)) {
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
        postEntity.setType(2);
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

    HttpOnNextListener verifyOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string, int code) {
            ToastUtil.showShortToast(getApplicationContext(), "验证码已发送!");
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(getApplicationContext(), getResources().getString(R.string.txt_server_error));
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };
    HttpOnNextListener setPassWordOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "Token失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            if (code == 501) {
                ToastUtil.showShortToast(getApplicationContext(), "验证码错误！");
                return;
            }
            if (code == 502) {
                ToastUtil.showShortToast(getApplicationContext(), "设置支付密码错误！");
                return;
            }
            String userInfo = SharedPreferencesUtils.getUserInfo(mContext);
            LoginResponseBean user = JSON.parseObject(userInfo, LoginResponseBean.class);
            UserInfo userInfoTemp = user.getUser();
            userInfoTemp.setSalt(Md5EncryptionHelper.SALT);
            user.setUser(userInfoTemp);
            userInfo = JSON.toJSONString(user);
            SharedPreferencesUtils.setUserInfo(mContext, userInfo);
            UserConfigs.loadUserInfo(userInfo);
            ToastUtil.showShortToast(getApplicationContext(), "支付密码设置成功!");
            finish();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(getApplicationContext(), getResources().getString(R.string.txt_server_error));
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {

    }
}
