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
import android.view.View;
import android.widget.TextView;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.umeng.analytics.MobclickAgent;

import zuo.biao.library.ui.statusbar.StatusBarUtils;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class UserIdenActivity extends CBaseActivity {
    protected Toolbar toolBar;
    private TextView nameTV, idenNumberTV;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, UserIdenActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iden);
        StatusBarUtils.setStatusBarColorDefault(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_user_iden));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        nameTV = findViewById(R.id.nameTV);
        idenNumberTV = findViewById(R.id.idenNumberTV);
    }

    @Override
    public void initData() {
        nameTV.setText(UserConfigs.getInstance().getName());
        idenNumberTV.setText(UserConfigs.getInstance().getCardNo());

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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("个人实名认证页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("个人实名认证页面");
        MobclickAgent.onPause(this);
    }
}
