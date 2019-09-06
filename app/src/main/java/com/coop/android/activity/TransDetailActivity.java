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
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.adapter.TransPagerAdapter;
import com.coop.android.fragment.ProjectInfoFragment;
import com.coop.android.fragment.TransInfoFragment;
import com.coop.android.view.CommonPopupWindow;
import com.coop.android.view.xtablayout.XTabLayout;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;
import zuo.biao.library.ui.statusbar.StatusBarUtils;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class TransDetailActivity extends CBaseActivity {
    private static final String TAG = "TransDetailActivity";
    //    private TabLayout tabLayout = null;
    private XTabLayout tabLayout = null;
    protected Toolbar toolBar;
    private ViewPager viewPager;

    private List<Fragment> mFragmentArrays;
    private List<String> mTabTitles;
    private String assetId;
    private String transNo;
    private CommonPopupWindow popupWindow;
    private String hashStr;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String assetId, String transNo) {
        return new Intent(context, TransDetailActivity.class).putExtra("assetId", assetId)
                .putExtra("transNo", transNo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_details);
        StatusBarUtils.setStatusBarColorDefault(this);
        intent = getIntent();
        assetId = intent.getStringExtra("assetId");
        transNo = intent.getStringExtra("transNo");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_trans_detail));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        mFragmentArrays = new ArrayList<Fragment>();
        mTabTitles = new ArrayList<String>();
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.tab_viewpager);
        mTabTitles.add("交易详情");
        mTabTitles.add("项目详情");
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        tabLayout.addTab(tabLayout.newTab().setText(mTabTitles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(mTabTitles.get(1)));
        mFragmentArrays.add(TransInfoFragment.createInstance());
        mFragmentArrays.add(ProjectInfoFragment.createInstance());
        PagerAdapter pagerAdapter = new TransPagerAdapter(getSupportFragmentManager(), mFragmentArrays);
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
        //避免TabLayout绑定ViewPager之后Tab被清掉，重新设置
        tabLayout.getTabAt(0).setText(mTabTitles.get(0));
        tabLayout.getTabAt(1).setText(mTabTitles.get(1));
    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                finish();
            }
        });
    }

    public String getAssetId() {
        return assetId;
    }

    public String getTransNo() {
        return transNo;
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("交易详情页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("交易详情页面");
        MobclickAgent.onPause(this);
    }
}
