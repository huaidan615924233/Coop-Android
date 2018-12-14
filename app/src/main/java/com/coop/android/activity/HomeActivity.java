package com.coop.android.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import zuo.biao.library.base.BaseActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    protected Button button, button2, button3, button4, button5, button6, button7;
    String[] permissions;
    private DrawableButton scanBtn;
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
        button7 = findViewById(R.id.button7);
        button.setOnClickListener(HomeActivity.this);
        button2.setOnClickListener(HomeActivity.this);
        button3.setOnClickListener(HomeActivity.this);
        button4.setOnClickListener(HomeActivity.this);
        button5.setOnClickListener(HomeActivity.this);
        button6.setOnClickListener(HomeActivity.this);
        button7.setOnClickListener(HomeActivity.this);
        helpTxt = findViewById(R.id.txtHelp);
        helpTxt.setOnClickListener(HomeActivity.this);
        scanBtn = findViewById(R.id.btnScan);
        scanBtn.setOnClickListener(HomeActivity.this);

        homeUserName = findView(R.id.homeUserName);
        homeHeaderImg = findView(R.id.homeHeaderImg);
        homeHeaderImg.setOnClickListener(HomeActivity.this);
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

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            startActivity(UpdatePasswordActivity.createIntent(HomeActivity.this, ConstantUtil.UPDATEPASSWORD));
        } else if (view.getId() == R.id.button2) {
            startActivity(LoginActivity.createIntent(HomeActivity.this));
        } else if (view.getId() == R.id.button3) {
            startActivity(SettingActivity.createIntent(HomeActivity.this));
        } else if (view.getId() == R.id.button4) {
            startActivity(LoginChooseActivity.createIntent(HomeActivity.this));
        } else if (view.getId() == R.id.button5) {
            startActivity(PayTokenActivity.createIntent(HomeActivity.this,"真格基金"));
        } else if (view.getId() == R.id.button6) {
            startActivity(AddTransactionDescActivity.createIntent(HomeActivity.this));
        } else if (view.getId() == R.id.button7) {
//            startActivity(LoginActivity.createIntent(HomeActivity.this));
        } else if (view.getId() == R.id.btnScan) {
//            startActivity(AboutUsActivity.createIntent(HomeActivity.this));
        } else if (view.getId() == R.id.txtHelp) {
            startActivity(AboutUsActivity.createIntent(HomeActivity.this));
        }else if (view.getId() == R.id.homeHeaderImg) {
            startActivity(PersionalActivity.createIntent(HomeActivity.this));
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
