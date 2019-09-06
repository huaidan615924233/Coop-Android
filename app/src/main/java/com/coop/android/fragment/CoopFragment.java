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
package com.coop.android.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.activity.LoginActivity;
import com.coop.android.adapter.AssetListAdapter;
import com.coop.android.adapter.CoopAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.interfaces.CoopListener;
import com.coop.android.model.AssetBean;
import com.coop.android.model.CoopEnterListResponseBean;
import com.coop.android.model.EnterTransListBean;
import com.coop.android.utils.GlideUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.MultiImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.swichlayout.SwitchLayout;
import zuo.biao.library.ui.indicator.CirclePageIndicator;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class CoopFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    public static final String TAG = "CoopFragment";
    private RecyclerView listRV;
    private SmartRefreshLayout refreshLayout;
    private CoopAdapter coopAdapter;
    private MultiImageView userHeaderImg;
    private TextView projectNameTV, projectLabelTV, projectTimeTV;
    private LinearLayout projectView, statusLLOne, statusLLTwo;
    private CoopListener coopListener;
    private AssetBean assetBean;   //当前资产
    private ViewPager mViewPager;
    private CirclePageIndicator mIndicator;

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static CoopFragment createInstance() {
        return new CoopFragment();
    }

    public void setCoopListener(CoopListener coopListener) {
        this.coopListener = coopListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_coop);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void initView() {
        listRV = findViewById(R.id.listRV);
        userHeaderImg = findViewById(R.id.userHeaderImg);
        projectNameTV = findViewById(R.id.projectNameTV);
        projectTimeTV = findViewById(R.id.projectTimeTV);
        projectLabelTV = findViewById(R.id.projectLabelTV);
        projectView = findViewById(R.id.projectView);
        statusLLOne = findViewById(R.id.statusLLOne);
        statusLLTwo = findViewById(R.id.statusLLTwo);
        refreshLayout = findViewById(R.id.refreshLayout);
        mViewPager = findViewById(R.id.mViewPager);
        mIndicator = findViewById(R.id.mIndicator);
        ClassicsFooter footer = new ClassicsFooter(context);
        footer.setTextSizeTitle(14);
        footer.setAccentColor(Color.parseColor("#ffffff"));
//        BezierRadarHeader header = new BezierRadarHeader(this);
//        header.setAccentColor(Color.parseColor("#ffffff"));
        ClassicsHeader header = new ClassicsHeader(context);
        footer.setTextSizeTitle(14);
        footer.setAccentColor(Color.parseColor("#ffffff"));
        refreshLayout.setRefreshHeader(header);
        refreshLayout.setRefreshFooter(footer);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);//取消内容不满一页时开启上拉加载功能
        refreshLayout.setEnableLoadmore(false); //禁用下拉加载
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        listRV.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void initData() {
        HttpPostApi postEntity = new HttpPostApi(coopEnterListOnNextListener, context, HttpPostApi.COOP_ENTER_LIST, true);
        postEntity.setUserId(UserConfigs.getInstance().getId());
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    @Override
    public void initEvent() {
        statusLLOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchLayout.get3DRotateFromRight(context, statusLLOne, false, null);
            }
        });
        statusLLTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchLayout.get3DRotateFromRight(context, statusLLTwo, false, null);
            }
        });
    }

    HttpOnNextListener coopEnterListOnNextListener = new HttpOnNextListener<CoopEnterListResponseBean>() {

        @Override
        public void onNext(CoopEnterListResponseBean coopEnterListResponseBean, int code) {
            listRV.setVisibility(View.GONE);
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
//            ToastUtil.showShortToast(context, coopResponseBean.toString());
            if (coopEnterListResponseBean == null || TextUtils.isEmpty(coopEnterListResponseBean.getId())) {
                refreshLayout.setVisibility(View.GONE);
                statusLLOne.setVisibility(View.VISIBLE);
                statusLLTwo.setVisibility(View.GONE);
                coopListener.setScanVisibility(false);
                return;
            }
//            String status = coopResponseBean.getProject().get(0).getStatus();
//            if (StringUtil.isEmpty(status) || status.equals("200")) {   //未审核
//                refreshLayout.setVisibility(View.GONE);
//                statusLLOne.setVisibility(View.VISIBLE);
//                statusLLTwo.setVisibility(View.GONE);
//                coopListener.setScanVisibility(false);
//                return;
//            }
//            if (status.equals("201") || status.equals("313")) {   //审核中
//                refreshLayout.setVisibility(View.GONE);
//                statusLLOne.setVisibility(View.GONE);
//                statusLLTwo.setVisibility(View.VISIBLE);
//                coopListener.setScanVisibility(false);
//                return;
//            }
            refreshLayout.setVisibility(View.VISIBLE);
            statusLLOne.setVisibility(View.GONE);
            statusLLTwo.setVisibility(View.GONE);
            coopListener.setScanVisibility(true);
            String projectName = coopEnterListResponseBean.getName();
            projectView.setVisibility(View.VISIBLE);
            projectNameTV.setText(projectName == null ? " " : projectName);
            String projectTime = coopEnterListResponseBean.getFound_time();
            if (!StringUtil.isEmpty(projectTime)) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectTime);
                    String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                    projectTimeTV.setText(now);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            projectLabelTV.setText(coopEnterListResponseBean.getType());
            GlideUtils.loadImage(context, AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + coopEnterListResponseBean.getLogo(), userHeaderImg, R.mipmap.default_project_header);
//            String projectTime = projectBean.getFoundTime();
//            if (!StringUtil.isEmpty(projectTime)) {
//                try {
//                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectTime);
//                    String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
//                    projectTimeTV.setText(now);
//
//                } catch (Exception e) {
//                    Log.e(TAG, e.getMessage());
//                }
//            }
            //资产列表
            final List<AssetBean> assetBeans = coopEnterListResponseBean.getAsset_list();
            if (assetBeans != null && assetBeans.size() != 0) {
//                AssetBean assetBean = new AssetBean();
//                assetBean.setId(assetBeans.get(0).getId());
//                assetBean.setProject_token("213123123");
//                assetBean.setProject_amount("22222");
//                assetBean.setStock_percent("0.1");
//                assetBean.setToken_name("AAAA");
//                assetBeans.add(assetBean);
//                assetBeans.add(assetBean);
                mViewPager.setAdapter(new AssetListAdapter(context, assetBeans));
                mViewPager.setCurrentItem(0);
                mIndicator.setVisibility(View.VISIBLE);
                mIndicator.setViewPager(mViewPager);
                setAssetBean(assetBeans.get(0));
                getTransList(assetBeans.get(0).getId());
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        setAssetBean(assetBeans.get(i));
                        getTransList(assetBeans.get(i).getId());
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
            }
        }

        @Override
        public void onError(Throwable e) {
            refreshLayout.setVisibility(View.GONE);
            statusLLOne.setVisibility(View.GONE);
            statusLLTwo.setVisibility(View.GONE);
            coopListener.setScanVisibility(false);
            ToastUtil.showShortToast(context, e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    /**
     * 资产交易list
     */
    private void getTransList(String assetId) {
        HttpPostApi postEntity = new HttpPostApi(coopEnterTransListOnNextListener, context, HttpPostApi.ENTER_TRANS_LIST, false);
        postEntity.setAssetId(assetId);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    HttpOnNextListener coopEnterTransListOnNextListener = new HttpOnNextListener<EnterTransListBean>() {
        @Override
        public void onNext(EnterTransListBean enterTransListBean, int code) {
            listRV.setVisibility(View.GONE);
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
            if (enterTransListBean.getTrans_list() == null || enterTransListBean.getTrans_list().size() == 0)
                return;
            listRV.setVisibility(View.VISIBLE);
            coopAdapter = new CoopAdapter(context, enterTransListBean.getTrans_list());
            listRV.setAdapter(coopAdapter);
            listRV.setHasFixedSize(true);
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(context, e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore(true);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore(true);
    }

    public AssetBean getAssetBean() {
        return assetBean;
    }

    public void setAssetBean(AssetBean assetBean) {
        this.assetBean = assetBean;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("创业者首页");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("创业者首页");
    }
}
