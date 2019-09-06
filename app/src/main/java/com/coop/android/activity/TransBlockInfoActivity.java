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
import android.view.View;
import android.widget.TextView;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.model.AssetBean;
import com.coop.android.model.TransDetailBean;
import com.umeng.analytics.MobclickAgent;

import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class TransBlockInfoActivity extends CBaseActivity {
    private static final String TAG = "TransBlockInfoActivity";
    protected Toolbar toolBar;
    private TextView transBlockHashTV, transNoTV, transTypeTV, assetTypeTV, transPartnerTV, transTokenTV, transBlockHeightTV, transTimeTV, transEnterTV;
    private TransDetailBean transDetailBean;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, TransDetailBean transDetailBean) {
        return new Intent(context, TransBlockInfoActivity.class).putExtra("transDetail", transDetailBean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_block_info);
        StatusBarUtils.setStatusBarColorDefault(this);
        intent = getIntent();
        transDetailBean = intent.getParcelableExtra("transDetail");
        Log.e(TAG, transDetailBean.toString());
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_trans_block_info));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        transBlockHashTV = findViewById(R.id.transBlockHashTV);
        transNoTV = findViewById(R.id.transNoTV);
        transTypeTV = findViewById(R.id.transTypeTV);
        assetTypeTV = findViewById(R.id.assetTypeTV);
        transPartnerTV = findViewById(R.id.transPartnerTV);
        transEnterTV = findViewById(R.id.transEnterTV);
        transTokenTV = findViewById(R.id.transTokenTV);
        transBlockHeightTV = findViewById(R.id.transBlockHeightTV);
        transTimeTV = findViewById(R.id.transTimeTV);
    }

    @Override
    public void initData() {
        if (transDetailBean == null || TextUtils.isEmpty(transDetailBean.getId()))
            return;
        transBlockHashTV.setText(transDetailBean.getTrans_hash());
        transNoTV.setText(transDetailBean.getTransaction_id());
//        AssetTypeTV.setText(transDetailBean.g());
//        transTypeTV.setText(transDetailBean.getTrans_hash());
        switch (transDetailBean.getAsset_type()) {
            case AssetBean.ASSET_TYPE_1:
                assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_1);
                break;
            case AssetBean.ASSET_TYPE_2:
                assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_2);
                break;
            case AssetBean.ASSET_TYPE_3:
                assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_3);
                break;
            case AssetBean.ASSET_TYPE_4:
                assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_4);
                break;
            default:
                break;
        }
        transPartnerTV.setText(transDetailBean.getInve_real_name());
        transEnterTV.setText(transDetailBean.getEntr_real_name());
        transTokenTV.setText(transDetailBean.getToken_num());
        transBlockHeightTV.setText(transDetailBean.getTrans_b_height());
        transTimeTV.setText(transDetailBean.getTrans_b_timestamp());
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("交易区块信息页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("交易区块信息页面");
        MobclickAgent.onPause(this);
    }
}
