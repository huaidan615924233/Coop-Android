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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class QrcodeActivity extends CBaseActivity {
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
        qrcodeImg = findViewById(R.id.image_content);
    }

    @Override
    public void initData() {
        //生成带logo的二维码图片
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", UserConfigs.getInstance().getNickName());
        jsonObject.put("desc", paymentDesc);
        jsonObject.put("userId", UserConfigs.getInstance().getId());
        jsonObject.put("name", UserConfigs.getInstance().getName());
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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("二维码页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("二维码页面");
        MobclickAgent.onPause(this);
    }
}
