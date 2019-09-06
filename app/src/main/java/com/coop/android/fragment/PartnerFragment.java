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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.activity.LoginActivity;
import com.coop.android.adapter.PartnerAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.CoopPartnerListResponseBean;
import com.coop.android.model.CoopPartnerResponseBean;
import com.coop.android.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class PartnerFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    public static final String TAG = "PartnerFragment";
    private RecyclerView listRV;
    private SmartRefreshLayout refreshLayout;
    private PartnerAdapter partnerAdapter;
    private ScrollView noDataLL;

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static PartnerFragment createInstance() {
        return new PartnerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_partner);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        listRV = findViewById(R.id.listRV);
        noDataLL = findViewById(R.id.noDataLL);

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
        HttpPostApi postEntity = new HttpPostApi(coopProjectListOnNextListener, context, HttpPostApi.COOP_INVE_LIST, true);
        postEntity.setUserId(UserConfigs.getInstance().getId());
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    @Override
    public void initEvent() {
    }

    HttpOnNextListener coopProjectListOnNextListener = new HttpOnNextListener<CoopPartnerListResponseBean>() {

        @Override
        public void onNext(CoopPartnerListResponseBean coopPartnerListResponseBean, int code) {
            noDataLL.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
//            ToastUtil.showShortToast(context, coopResponseBean.toString());
            if (coopPartnerListResponseBean.getTrans() == null || coopPartnerListResponseBean.getTrans().size() == 0) {
                return;
            }
            noDataLL.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
            partnerAdapter = new PartnerAdapter(context, coopPartnerListResponseBean.getTrans());
            listRV.setAdapter(partnerAdapter);
            listRV.setHasFixedSize(true);
        }

        @Override
        public void onError(Throwable e) {
            refreshLayout.setVisibility(View.GONE);
            noDataLL.setVisibility(View.VISIBLE);
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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("资源方首页");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("资源方首页");
    }
}
