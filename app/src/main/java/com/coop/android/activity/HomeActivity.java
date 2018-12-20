package com.coop.android.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coop.android.R;
import com.coop.android.utils.CheckPermissionUtils;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.coop.android.view.DrawableButton;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.Log;

public class HomeActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private static final String TAG = "HomeActivity";
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;
    protected Button button, button2, button3, button4, button5, button6;
    String[] permissions;
    private DrawableButton scanBtn, btnQrcode;
    private TextView helpTxt;
    private TextView homeUserName;
    private CircleImageView homeHeaderImg;
    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    /**
     * 进入主页
     *
     * @return
     */
    public static void newInstance(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        helpTxt = findViewById(R.id.txtHelp);
        scanBtn = findViewById(R.id.btnScan);
        btnQrcode = findViewById(R.id.btnQrcode);
        homeUserName = findViewById(R.id.homeUserName);
        homeHeaderImg = findViewById(R.id.homeHeaderImg);
    }

    @Override
    public void initData() {
        //初始化权限
        CheckPermissionUtils.initPermission(context);
    }

    @Override
    public void initEvent() {
        button.setOnClickListener(HomeActivity.this);
        button2.setOnClickListener(HomeActivity.this);
        button3.setOnClickListener(HomeActivity.this);
        button4.setOnClickListener(HomeActivity.this);
        button5.setOnClickListener(HomeActivity.this);
        button6.setOnClickListener(HomeActivity.this);
        helpTxt.setOnClickListener(HomeActivity.this);
        scanBtn.setOnClickListener(HomeActivity.this);
        btnQrcode.setOnClickListener(HomeActivity.this);
        homeHeaderImg.setOnClickListener(HomeActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            startActivity(UpdatePasswordActivity.createIntent(mContext, ConstantUtil.UPDATEPASSWORD));
        } else if (view.getId() == R.id.button2) {
            startActivity(LoginActivity.createIntent(mContext));
        } else if (view.getId() == R.id.button3) {
            startActivity(SettingActivity.createIntent(mContext));
        } else if (view.getId() == R.id.button4) {
            startActivity(LoginChooseActivity.createIntent(mContext));
        } else if (view.getId() == R.id.button5) {
            startActivity(PayTokenActivity.createIntent(mContext, "真格基金"));
        } else if (view.getId() == R.id.button6) {
            startActivity(AddTransactionDescActivity.createIntent(mContext));
        } else if (view.getId() == R.id.btnQrcode) {
            startActivity(QrcodeActivity.createIntent(mContext));
        } else if (view.getId() == R.id.btnScan) {
            cameraTask();
        } else if (view.getId() == R.id.txtHelp) {
            startActivity(AboutUsActivity.createIntent(mContext));
        } else if (view.getId() == R.id.homeHeaderImg) {
            startActivity(PersionalActivity.createIntent(mContext));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.showShortToast(mContext, "解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showShortToast(mContext, "解析二维码失败");
                }
            }
        }
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

    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            startActivityForResult(ScanActivity.createIntent(mContext), REQUEST_CODE);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求camera权限",
                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
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
            new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(REQUEST_CAMERA_PERM)
                    .build()
                    .show();
        }
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
                ToastUtil.showShortToast(HomeActivity.this, "再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }

        return super.onKeyUp(keyCode, event);
    }

}
