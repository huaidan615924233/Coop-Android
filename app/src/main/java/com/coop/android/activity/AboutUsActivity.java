package com.coop.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.coop.android.R;
import com.coop.android.utils.TextViewSpanUtil;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.ExpandableTextView;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class AboutUsActivity extends BaseActivity {
    protected Toolbar toolBar;
    private TextView aboutDesc, expandTV;
    private boolean isExpanded = false;
    private String aboutUs;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, AboutUsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        aboutUs = "\t\t标题平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明\n" +
                "平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明\n" +
                "平台说明平台说\n明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平台说明平\n台说明平台说明平台说明";
        tvBaseTitle.setText(getString(R.string.txt_title_about_us));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        aboutDesc = findViewById(R.id.aboutDesc);
        expandTV = findViewById(R.id.expandTV);
        aboutDesc.setText(aboutUs);
        expandTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    isExpanded = false;
                    aboutDesc.setMaxLines(2);// 收起
                    expandTV.setText("展开全部");
                } else {
                    isExpanded = true;
                    aboutDesc.setMaxLines(Integer.MAX_VALUE);// 展开
                    expandTV.setText("收起全部");
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
