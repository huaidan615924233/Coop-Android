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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.adapter.TransPagerAdapter;
import com.coop.android.fragment.ProjectInfoFragment;
import com.coop.android.fragment.TransInfoFragment;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CommonPopupWindow;
import com.coop.android.view.xtablayout.XTabLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.util.CommonUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class TransDetailActivity extends CBaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "TransDetailActivity";
//    private TabLayout tabLayout = null;
    private XTabLayout tabLayout = null;

    private ViewPager viewPager;

    private List<Fragment> mFragmentArrays;
    private List<String> mTabTitles;
    private String projectId;
    private String transNo;
    private String account;
    private ImageView backIV;
    private CommonPopupWindow popupWindow;
    private String hashStr;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String projectId, String transNo, String account) {
        return new Intent(context, TransDetailActivity.class).putExtra("projectId", projectId)
                .putExtra("transNo", transNo).putExtra("account", account);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_details);
        intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        transNo = intent.getStringExtra("transNo");
        account = intent.getStringExtra("account");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        backIV = findViewById(R.id.backIV);
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
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                finish();
            }
        });
    }

    //全屏弹出
    public void showAll(View view, String hashStr) {
        this.hashStr = hashStr;
        if (popupWindow != null && popupWindow.isShowing()) return;
        View popView = LayoutInflater.from(context).inflate(R.layout.popup_trans_info, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(popView);
        popupWindow = new CommonPopupWindow.Builder(context)
                .setView(R.layout.popup_trans_info)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, popView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setOutsideTouchable(true)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_trans_info:
                final TextView blockChinaHashTV = view.findViewById(R.id.blockChinaHashTV);
                blockChinaHashTV.setText(hashStr);
                LinearLayout blockChinaHashLL = view.findViewById(R.id.blockChinaHashLL);
                blockChinaHashLL.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, blockChinaHashTV.getText().toString()));
                        ToastUtil.showShortToast(mContext, "哈希串已复制到剪贴板");
                        return true;
                    }
                });
                break;
            default:
                break;
        }
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTransNo() {
        return transNo;
    }

    public String getAccount() {
        return account;
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
