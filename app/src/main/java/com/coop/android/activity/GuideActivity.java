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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import zuo.biao.library.ui.indicator.CirclePageIndicator;

public class GuideActivity extends CBaseActivity {
    private ViewPager mViewPagerView;

    private ArrayList<View> mGuideList;
    private CirclePageIndicator mPageIndicator;
    private Button guideBbtn;

    /**
     * 进入引导页
     *
     * @return
     */
    public static void newInstance(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        mViewPagerView = findViewById(R.id.vpager);
        guideBbtn = findViewById(R.id.id_guide_btn);
        mPageIndicator = findViewById(R.id.id_guide_indicator);

        View view1 = LayoutInflater.from(this).inflate(R.layout.guide_layout_1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.guide_layout_1, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.guide_layout_1, null);
//        View view4 = LayoutInflater.from(this).inflate(R.layout.guide4, null);
        initView(view1, R.mipmap.ic_guide_logo_1, R.string.txt_guide_title_1, R.string.txt_guide_title_desc_1);
        initView(view2, R.mipmap.ic_guide_logo_2, R.string.txt_guide_title_2, R.string.txt_guide_title_desc_2);
        initView(view3, R.mipmap.ic_guide_logo_3, R.string.txt_guide_title_3, R.string.txt_guide_title_desc_3);
        mGuideList = new ArrayList<>();
        mGuideList.add(view1);
        mGuideList.add(view2);
        mGuideList.add(view3);
//        guideList.add(view4);
    }

    @Override
    public void initData() {
        mViewPagerView.setAdapter(new ViewPageAdapter(mGuideList));
        mViewPagerView.setCurrentItem(0);
        mPageIndicator.setViewPager(mViewPagerView);
    }

    @Override
    public void initEvent() {

        mViewPagerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position < mGuideList.size() - 1) {
                    guideBbtn.setVisibility(View.INVISIBLE);
                } else {
                    guideBbtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        guideBbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.setFirstRun(mContext, false);
                startActivity(LoginActivity.createIntent(GuideActivity.this, false));
                finish();
            }
        });
    }


    private void initView(View content, int icGuideLogo, int tvGuideTitle, int tvGuideLogoDesc) {
        ImageView logoView = content.findViewById(R.id.id_guide_iv_logo);
        TextView titleView = content.findViewById(R.id.id_guide_txt_title);
        TextView descView = content.findViewById(R.id.id_guide_txt_title_desc);
        logoView.setImageResource(icGuideLogo);
//        content.setBackgroundResource(icGuideLogo);
        titleView.setText(tvGuideTitle);
        descView.setText(tvGuideLogoDesc);
    }

    private class ViewPageAdapter extends PagerAdapter {
        ArrayList<View> mList;

        ViewPageAdapter(ArrayList<View> list) {
            this.mList = list;
        }

        @Override
        public int getCount() {
            return (mList == null || mList.size() == 0) ? 0 : mList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 加载布局
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 销毁布局，即从一个布局页面滑动到另一个布局页面
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("欢迎页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("欢迎页面");
        MobclickAgent.onPause(this);
    }
}
