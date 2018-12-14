package com.coop.android.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coop.android.R;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.PermissionsUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.coop.android.view.CommonPopupWindow;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.CommonUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class PersionalActivity extends BaseActivity implements View.OnClickListener, CommonPopupWindow.ViewInterface {
    private CircleImageView userHeaderImg;
    private TextView userName, userId;
    private LinearLayout setPassLL, callMeLL, settingLL;
    protected Toolbar toolBar;
    private CommonPopupWindow popupWindow;
    private String companyPhone;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, PersionalActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                finish();
            }
        });
        userHeaderImg = findViewById(R.id.userHeaderImg);
        userName = findViewById(R.id.userName);
        userId = findViewById(R.id.userId);
        setPassLL = findViewById(R.id.setPassLL);
        callMeLL = findViewById(R.id.callMeLL);
        settingLL = findViewById(R.id.settingLL);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        setPassLL.setOnClickListener(PersionalActivity.this);
        callMeLL.setOnClickListener(PersionalActivity.this);
        settingLL.setOnClickListener(PersionalActivity.this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.setPassLL:
                toActivity(UpdatePasswordActivity.createIntent(PersionalActivity.this, ConstantUtil.SETPASSWORD));
                break;
            case R.id.callMeLL:
                showAll(v);
                break;
            case R.id.settingLL:
                toActivity(SettingActivity.createIntent(PersionalActivity.this));
                break;
            default:
                break;
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_call_me:
                TextView cancelTV = view.findViewById(R.id.cancelTV);
                cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                TextView copyTV = view.findViewById(R.id.copyTV);
                TextView companyWchat = view.findViewById(R.id.companyWchat);
                final String wChat = companyWchat.getText().toString();
                copyTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, wChat));
                        ToastUtil.showShortToast(PersionalActivity.this, "微信已复制到剪贴板");
                    }
                });
                TextView callPhoneTV = view.findViewById(R.id.callPhoneTV);
                TextView companyPhoneTV = view.findViewById(R.id.companyPhone);
                companyPhone = companyPhoneTV.getText().toString();
                callPhoneTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
                        // PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
                        //这里的this不是上下文，是Activity对象！
                        PermissionsUtils.getInstance().chekPermissions(PersionalActivity.this, permissions, permissionsResult);
                    }
                });
                break;
            default:
                break;
        }
    }

    //全屏弹出
    public void showAll(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View popView = LayoutInflater.from(this).inflate(R.layout.popup_call_me, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(popView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_call_me)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, popView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setOutsideTouchable(false)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            CommonUtil.call(PersionalActivity.this, companyPhone);
        }

        @Override
        public void forbitPermissons() {
            ToastUtil.showShortToast(PersionalActivity.this, "请开启拨打电话的权限");
        }
    };

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }
}

