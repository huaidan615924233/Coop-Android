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
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class AddTransactionDescActivity extends CBaseActivity implements View.OnClickListener {
    protected Toolbar toolBar;
    private EditText descET;
    private Button nextBtn;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, AddTransactionDescActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtransaction);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtil.isEmpty(descET.getText().toString())) {
                    new AlertDialog(mContext, "提示", "您确定要放弃本次通证转让吗?", true, 0, new AlertDialog.OnDialogButtonClickListener() {
                        @Override
                        public void onDialogButtonClick(int requestCode, boolean isPositive) {
                            if (isPositive)
                                finish();
                        }
                    }).show();
                    return;
                }
                finish();
            }
        });
        descET = findViewById(R.id.descET);
        nextBtn = findViewById(R.id.nextBtn);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                if (TextUtils.isEmpty(descET.getText().toString())) {
                    ToastUtil.showShortToast(mContext, "请输入备注信息");
                    return;
                }
                startActivity(QrcodeActivity.createIntent(mContext, descET.getText().toString()));
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    /**
     * 返回提示
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (!StringUtil.isEmpty(descET.getText().toString())) {
                new AlertDialog(mContext, "提示", "您确定要放弃本次通证转让吗?", true, 0, new AlertDialog.OnDialogButtonClickListener() {
                    @Override
                    public void onDialogButtonClick(int requestCode, boolean isPositive) {
                        if (isPositive)
                            finish();
                    }
                }).show();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("二维码添加备注页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("二维码添加备注页面");
        MobclickAgent.onPause(this);
    }
}
