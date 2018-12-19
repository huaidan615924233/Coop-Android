package com.coop.android.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coop.android.R;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.PermissionsUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.coop.android.view.DrawableButton;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import zuo.biao.library.base.BaseActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
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
        //相机权限和读写权限
        permissions = new String[]{Manifest.permission.CAMERA};
        // PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(HomeActivity.this, permissions, permissionsResult);
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
            startActivityForResult(ScanActivity.createIntent(mContext), REQUEST_CODE);
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

    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            scanBtn.setOnClickListener(HomeActivity.this);
        }

        @Override
        public void forbitPermissons() {
//            finish();
            scanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HomeActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
                    PermissionsUtils.getInstance().chekPermissions(HomeActivity.this, permissions, permissionsResult);
                }
            });
        }
    };

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
