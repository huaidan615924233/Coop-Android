package com.coop.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.coop.android.R;
import com.coop.android.utils.ConstantUtil;

import zuo.biao.library.base.BaseActivity;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    protected Toolbar toolBar;
    private LinearLayout logoutLL, chooseUsesLL, aboutUsLL;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_setting));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        aboutUsLL = findViewById(R.id.aboutUsLL);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        aboutUsLL.setOnClickListener(SettingActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutUsLL:
                toActivity(AboutUsActivity.createIntent(SettingActivity.this));
                break;
            default:
                break;
        }
    }
}
