package com.coop.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.coop.android.R;
import com.coop.android.utils.ConstantUtil;

import zuo.biao.library.base.BaseActivity;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class UpdatePasswordActivity extends BaseActivity {
    private int type;
    protected Toolbar toolBar;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, int type) {
        return new Intent(context, UpdatePasswordActivity.class).putExtra("type",type);
    }

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
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
