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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.coop.android.jiguang.ExampleUtil;
import com.coop.android.jiguang.LocalBroadcastManager;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import cn.jpush.android.api.JPushInterface;
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
    public static boolean isForeground = false;

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.coop.android.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

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
        registerMessageReceiver();
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
        jsonObject.put("registrationId", JPushInterface.getRegistrationID(mContext));
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

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                        JSONObject object = JSON.parseObject(extras);
                        JSONObject transObject = object.getJSONObject("message_extras_key");

                        mContext.startActivity(TransVoucherActivity.createIntent(context, "(wo)", "",
                                transObject.get("tokenNum").toString(), transObject.get("transTime").toString(),
                                transObject.get("entrCustName").toString(), transObject.get("inveCustName").toString()));
                        finish();
                    }
                    Log.e(TAG, showMsg.toString());
//                    ToastUtil.showShortToast(mContext, showMsg.toString());
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isForeground = true;
        MobclickAgent.onPageStart("二维码页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        isForeground = false;
        MobclickAgent.onPageEnd("二维码页面");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
