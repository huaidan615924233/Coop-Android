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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class LoginChooseAssetTypeActivity extends CBaseActivity implements View.OnClickListener {
    public static final String TAG = "LoginChooseAssetTypeActivity";
    protected Toolbar toolBar;
    private String roleType;
    private Button nextBtn;
    private LinearLayout enterSelectedLL, enterUnSelectedLL, partnerSelectedLL, partnerUnSelectedLL;
    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginChooseAssetTypeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choose_assettype);
        StatusBarUtils.setStatusBarColorDefault(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_login_user_choose_asset_type));
//        toolBar = findViewById(R.id.toolbar_img);
//        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
//        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        nextBtn = findViewById(R.id.nextBtn);
        enterSelectedLL = findViewById(R.id.enterSelectedLL);
        enterUnSelectedLL = findViewById(R.id.enterUnSelectedLL);
        partnerSelectedLL = findViewById(R.id.partnerSelectedLL);
        partnerUnSelectedLL = findViewById(R.id.partnerUnSelectedLL);
    }

    @Override
    public void initData() {
        if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            enterUnSelectedLL.setEnabled(false);
            roleType = ConstantUtil.PARTNERIDEN;
            enterSelectedLL.setVisibility(View.GONE);
            enterUnSelectedLL.setVisibility(View.VISIBLE);
            partnerSelectedLL.setVisibility(View.VISIBLE);
            partnerUnSelectedLL.setVisibility(View.GONE);
        } else if (ConstantUtil.PARTNERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            partnerUnSelectedLL.setEnabled(false);
            roleType = ConstantUtil.ENTERIDEN;
            enterSelectedLL.setVisibility(View.VISIBLE);
            enterUnSelectedLL.setVisibility(View.GONE);
            partnerSelectedLL.setVisibility(View.GONE);
            partnerUnSelectedLL.setVisibility(View.VISIBLE);
        } else {
            enterUnSelectedLL.setEnabled(true);
            partnerUnSelectedLL.setEnabled(true);
            roleType = ConstantUtil.ENTERIDEN;
            enterSelectedLL.setVisibility(View.VISIBLE);
            enterUnSelectedLL.setVisibility(View.GONE);
            partnerSelectedLL.setVisibility(View.GONE);
            partnerUnSelectedLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initEvent() {
        nextBtn.setOnClickListener(this);
        enterUnSelectedLL.setOnClickListener(this);
        partnerUnSelectedLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                if (TextUtils.isEmpty(UserConfigs.getInstance().getCardNo())) {
                    String userInfo = SharedPreferencesUtils.getUserInfo(mContext);
                    LoginResponseBean user = JSON.parseObject(userInfo, LoginResponseBean.class);
                    UserInfo userInfoTemp = user.getUser();
                    userInfoTemp.setLastLoginRole(roleType);
                    user.setUser(userInfoTemp);
                    userInfo = JSON.toJSONString(user);
                    SharedPreferencesUtils.setUserInfo(mContext, userInfo);
                    UserConfigs.loadUserInfo(userInfo);
                    startActivity(LoginIdenActivity.createIntent(mContext));
                } else
                    addRole(roleType);
                break;
            case R.id.enterUnSelectedLL:
                roleType = ConstantUtil.ENTERIDEN;
                enterSelectedLL.setVisibility(View.VISIBLE);
                enterUnSelectedLL.setVisibility(View.GONE);
                partnerSelectedLL.setVisibility(View.GONE);
                partnerUnSelectedLL.setVisibility(View.VISIBLE);
                break;
            case R.id.partnerUnSelectedLL:
                roleType = ConstantUtil.PARTNERIDEN;
                enterSelectedLL.setVisibility(View.GONE);
                enterUnSelectedLL.setVisibility(View.VISIBLE);
                partnerSelectedLL.setVisibility(View.VISIBLE);
                partnerUnSelectedLL.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void addRole(String roleType) {
        HttpPostApi postEntity = new HttpPostApi(addRoleOnNextListener, this, HttpPostApi.AUTHENTICATION, true);
        postEntity.setNickName(TextUtils.isEmpty(UserConfigs.getInstance().getNickName()) ? "" : UserConfigs.getInstance().getNickName());
        postEntity.setRoleType(roleType);
        postEntity.setUserId(UserConfigs.getInstance().getId());
        postEntity.setName(TextUtils.isEmpty(UserConfigs.getInstance().getName()) ? "" : UserConfigs.getInstance().getName());
        postEntity.setCardNo(TextUtils.isEmpty(UserConfigs.getInstance().getCardNo()) ? "" : UserConfigs.getInstance().getCardNo());
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    HttpOnNextListener addRoleOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            if (code == 501) {
                ToastUtil.showShortToast(getApplicationContext(), "第三方错误信息!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            if (code == 502) {
                ToastUtil.showShortToast(getApplicationContext(), "姓名与身份证不匹配!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            if (code == 503) {
                ToastUtil.showShortToast(getApplicationContext(), "申请用户数字证书错误!");
                startActivity(LoginActivity.createIntent(mContext, true));
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
//            ToastUtil.showShortToast(getApplicationContext(), "角色创建成功!");
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
        MobclickAgent.onPageStart("选择角色页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("选择角色页面");
        MobclickAgent.onPause(this);
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
                ToastUtil.showShortToast(LoginChooseAssetTypeActivity.this, "再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }

        return super.onKeyUp(keyCode, event);
    }
}
