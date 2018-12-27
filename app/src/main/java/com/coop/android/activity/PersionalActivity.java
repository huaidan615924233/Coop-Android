package com.coop.android.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.coop.android.view.CommonPopupWindow;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class PersionalActivity extends BaseActivity implements View.OnClickListener, CommonPopupWindow.ViewInterface, EasyPermissions.PermissionCallbacks {
    public static final String TAG = "PersionalActivity";
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
        userName.setText(UserConfigs.getInstance().getNickName());
        userId.setText("ID:" + UserConfigs.getInstance().getCustNo());
        if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            setPassLL.setVisibility(View.VISIBLE);
            callMeLL.setBackgroundResource(R.drawable.bg_frame_white_to_gray);
        } else {
            setPassLL.setVisibility(View.GONE);
            callMeLL.setBackgroundResource(R.drawable.bg_left_radius_frame_white_to_gray);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
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
                        callPhoneTask();
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

    /**
     * EsayPermissions接管权限处理逻辑
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(ConstantUtil.PERMISSIONS_REQUEST_CALL_PHONE_PERM)
    public void callPhoneTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            // Have permission, do the thing!
            CommonUtil.call(PersionalActivity.this, companyPhone);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求打电话权限",
                    ConstantUtil.PERMISSIONS_REQUEST_CALL_PHONE_PERM, Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e(TAG, "执行onPermissionsGranted()...");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e(TAG, "执行onPermissionsDenied()...");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请打电话权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(ConstantUtil.PERMISSIONS_REQUEST_CALL_PHONE_PERM)
                    .build()
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }
}

