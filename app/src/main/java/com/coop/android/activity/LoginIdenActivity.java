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
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.LoginResponseBean;
import com.coop.android.model.UserInfo;
import com.coop.android.utils.IDCardValidate;
import com.coop.android.utils.SharedPreferencesUtils;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class LoginIdenActivity extends CBaseActivity implements View.OnClickListener {
    public static final String TAG = "LoginIdenActivity";
    protected Toolbar toolBar;
    private EditText userNameET, userIdenNumberET, userNickNameET;
    private TextView idenLableTV;
    private Button nextBtn;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginIdenActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_iden);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_login_user_iden));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userNameET = findViewById(R.id.userNameET);
        idenLableTV = findViewById(R.id.idenLableTV);
        userIdenNumberET = findViewById(R.id.userIdenNumberET);
        userNickNameET = findViewById(R.id.userNickNameET);
        nextBtn = findViewById(R.id.nextBtn);
        userIdenNumberET.setFilters(new InputFilter[]{inputFilter});
        userNickNameET.setFilters(new InputFilter[]{inputFilterUserNickName});
    }

    @Override
    public void initData() {
        userNameET.setFocusableInTouchMode(true);
        userNameET.setFocusable(true);
        userNameET.requestFocus();
        nextBtn.setEnabled(false);
        nextBtn.setBackgroundResource(R.drawable.btn_background_unenable_border_new);
    }

    @Override
    public void initEvent() {
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                if (TextUtils.isEmpty(userNameET.getText().toString())) {
                    ToastUtil.showShortToast(getApplicationContext(), "姓名不能为空!");
                    return;
                }
                if (TextUtils.isEmpty(userNickNameET.getText().toString())) {
                    ToastUtil.showShortToast(getApplicationContext(), "昵称不能为空!");
                    return;
                }
                validateIden(userNameET.getText().toString(), userIdenNumberET.getText().toString());
                break;
            default:
                break;
        }
    }

    InputFilter inputFilterUserNickName = new InputFilter() {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9_\\u4E00-\\u9FA5]");

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            Matcher matcher = pattern.matcher(charSequence);
            if (!matcher.find()) {
                return null;
            } else {
                ToastUtil.showShortToast(mContext, "只能输入汉字,英文,数字和下划线");
                return "";
            }
        }
    };

    InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            String userIdenNumber = userIdenNumberET.getText().toString() + charSequence.toString();
            if (userIdenNumber.length() == 18) {
                if (IDCardValidate.validate_effective(userIdenNumber)) {
                    nextBtn.setEnabled(true);
                    nextBtn.setBackgroundResource(R.drawable.btn_background_border_new);
                    idenLableTV.setVisibility(View.INVISIBLE);
                } else {
                    nextBtn.setEnabled(false);
                    nextBtn.setBackgroundResource(R.drawable.btn_background_unenable_border_new);
                    idenLableTV.setVisibility(View.VISIBLE);
                }
            } else if (userIdenNumber.length() > 18) {
                return "";
            } else if (userIdenNumber.length() < 18) {
                nextBtn.setEnabled(false);
                nextBtn.setBackgroundResource(R.drawable.btn_background_unenable_border_new);
                idenLableTV.setVisibility(View.INVISIBLE);
            }
            if (i1 == 0) {  //删除操作
                nextBtn.setEnabled(false);
                nextBtn.setBackgroundResource(R.drawable.btn_background_unenable_border_new);
            }
            return null;
        }
    };

    private void validateIden(String name, String idenNumber) {
        HttpPostApi postEntity = new HttpPostApi(validateIdenOnNextListener, this, HttpPostApi.AUTHENTICATION, true);
        postEntity.setName(name);
        postEntity.setRoleType(UserConfigs.getInstance().getLastLoginRole());
        postEntity.setUserId(UserConfigs.getInstance().getId());
        postEntity.setCardNo(idenNumber);
        postEntity.setNickName(userNickNameET.getText().toString());
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    HttpOnNextListener validateIdenOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            if (code == 501) {
                ToastUtil.showShortToast(getApplicationContext(), "第三方错误信息!");
                return;
            }
            if (code == 502) {
                ToastUtil.showShortToast(getApplicationContext(), "姓名与身份证不匹配!");
                return;
            }
            if (code == 503) {
                ToastUtil.showShortToast(getApplicationContext(), "申请用户数字证书错误!");
                return;
            }
            String userInfo = SharedPreferencesUtils.getUserInfo(mContext);
            LoginResponseBean user = JSON.parseObject(userInfo, LoginResponseBean.class);
            String idenString = "";
            try {
                idenString = userIdenNumberET.getText().toString();
                idenString = idenString.substring(0, 2) + "**************" + idenString.substring(16, 18);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            UserInfo userInfoTemp = user.getUser();
            userInfoTemp.setName(userNameET.getText().toString());
            userInfoTemp.setCardNo(idenString);
            userInfoTemp.setNickName(userNickNameET.getText().toString());
            user.setUser(userInfoTemp);
            userInfo = JSON.toJSONString(user);
            SharedPreferencesUtils.setUserInfo(mContext, userInfo);
            UserConfigs.loadUserInfo(userInfo);
//            ToastUtil.showShortToast(getApplicationContext(), "身份证验证成功!");
//            if (TextUtils.isEmpty(UserConfigs.getInstance().getLastLoginRole()))
//                startActivity(LoginChooseActivity.createIntent(mContext));
//            else
            HomeActivity.newInstance(mContext);
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
        MobclickAgent.onPageStart("验证身份证页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("验证身份证页面");
        MobclickAgent.onPause(this);
    }
}
