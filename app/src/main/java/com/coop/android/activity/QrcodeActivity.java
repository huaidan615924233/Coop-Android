package com.coop.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class QrcodeActivity extends BaseActivity {
    public static final String TAG = "QrcodeActivity";
    protected Toolbar toolBar;
    private ImageView qrcodeImg;
    public Bitmap mBitmap = null;
    private String paymentDesc;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String desc) {
        return new Intent(context, QrcodeActivity.class).putExtra("desc", desc);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        intent = getIntent();
        paymentDesc = intent.getStringExtra("desc");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_down_btn);
        qrcodeImg = (ImageView) findViewById(R.id.image_content);
    }

    @Override
    public void initData() {
        //生成带logo的二维码图片
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", UserConfigs.getInstance().getNickName());
        jsonObject.put("desc", paymentDesc);
        jsonObject.put("userId", UserConfigs.getInstance().getId());
        String textContent = JSON.toJSONString(jsonObject);
        Log.e(TAG, textContent);
        mBitmap = CodeUtils.createImage(textContent, 500, 500, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        qrcodeImg.setImageBitmap(mBitmap);
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }
}
