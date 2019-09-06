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

import com.coop.android.BuildConfig;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.umeng.analytics.MobclickAgent;

import zuo.biao.library.ui.statusbar.StatusBarUtils;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class AboutUsActivity extends CBaseActivity {
    protected Toolbar toolBar;
    private TextView aboutDesc, versionDescTV;
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
        StatusBarUtils.setStatusBarColorDefault(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        aboutUs = "\t\t我们希望通过创新的技术与专业的服务帮助创业者解决在早期创业者遇到的各种问题。协助创业者以新的方式进行各项资源对接与交易，加速项目早期发展。";
        tvBaseTitle.setText(getString(R.string.txt_title_about_us));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        aboutDesc = findViewById(R.id.aboutDesc);
        versionDescTV = findViewById(R.id.versionDescTV);
        aboutDesc.setText(aboutUs);
        versionDescTV.setText(getResources().getString(R.string.app_name) + "@_v" + BuildConfig.VERSION_NAME);
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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("关于页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("关于页面");
        MobclickAgent.onPause(this);
    }
}
