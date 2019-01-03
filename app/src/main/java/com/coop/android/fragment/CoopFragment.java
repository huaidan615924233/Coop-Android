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
import android.widget.TextView;

import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.activity.LoginActivity;
import com.coop.android.activity.ProjectInfoActivity;
import com.coop.android.adapter.CoopAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.interfaces.CoopListener;
import com.coop.android.model.CoopResponseBean;
import com.coop.android.model.ProjectBean;
import com.coop.android.utils.NumUtils;
import com.coop.android.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.swichlayout.SwitchLayout;
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
    private TextView projectNameTV, projectTimeTV, projectTotalTV, tokenTotalTV, tokenPerTV;
    private LinearLayout projectView, statusLLOne, statusLLTwo;
    private CoopListener coopListener;
    private ProjectBean projectBean;   //当前项目

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
        projectNameTV = findViewById(R.id.projectNameTV);
        projectTimeTV = findViewById(R.id.projectTimeTV);
        projectTotalTV = findViewById(R.id.projectTotalTV);
        tokenTotalTV = findViewById(R.id.tokenTotalTV);
        tokenPerTV = findViewById(R.id.tokenPerTV);
        projectView = findViewById(R.id.projectView);
        statusLLOne = findViewById(R.id.statusLLOne);
        statusLLTwo = findViewById(R.id.statusLLTwo);
        refreshLayout = findViewById(R.id.refreshLayout);
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
        HttpPostApi postEntity = new HttpPostApi(coopProjectListOnNextListener, context, HttpPostApi.COOP_PROJECT_LIST, true);
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

    HttpOnNextListener coopProjectListOnNextListener = new HttpOnNextListener<CoopResponseBean>() {

        @Override
        public void onNext(CoopResponseBean coopResponseBean, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(context, "Token失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
//            ToastUtil.showShortToast(context, coopResponseBean.toString());
            if (coopResponseBean.getProject() == null || coopResponseBean.getProject().get(0) == null) {
                refreshLayout.setVisibility(View.GONE);
                statusLLOne.setVisibility(View.VISIBLE);
                statusLLTwo.setVisibility(View.GONE);
                coopListener.setScanVisibility(false);
                return;
            }
            String status = coopResponseBean.getProject().get(0).getStatus();
            if (StringUtil.isEmpty(status) || status.equals("200")) {   //未审核
                refreshLayout.setVisibility(View.GONE);
                statusLLOne.setVisibility(View.VISIBLE);
                statusLLTwo.setVisibility(View.GONE);
                coopListener.setScanVisibility(false);
                return;
            }
            if (status.equals("201") || status.equals("313")) {   //审核中
                refreshLayout.setVisibility(View.GONE);
                statusLLOne.setVisibility(View.GONE);
                statusLLTwo.setVisibility(View.VISIBLE);
                coopListener.setScanVisibility(false);
                return;
            }
            refreshLayout.setVisibility(View.VISIBLE);
            statusLLOne.setVisibility(View.GONE);
            statusLLTwo.setVisibility(View.GONE);
            coopListener.setScanVisibility(true);
            final ProjectBean projectBean = coopResponseBean.getProject().get(0);
            setProject(projectBean);
            String projectName = projectBean.getName();
            projectNameTV.setText(projectName == null ? " " : projectName);
            String projectTime = projectBean.getFoundTime();
            if (!StringUtil.isEmpty(projectTime)) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectTime);
                    String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                    projectTimeTV.setText(now);
                    tokenPerTV.setText(String.format("%.0f", Double.parseDouble(projectBean.getStockPercent()) * 100) + "%");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            projectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(ProjectInfoActivity.createIntent(context, projectBean.getId()));
                }
            });
            projectTotalTV.setText(NumUtils.formatNum(projectBean.getProjectAmount(), false));
            tokenTotalTV.setText(projectBean.getProjectToken());
            coopAdapter = new CoopAdapter(context, projectBean.getList());
            listRV.setAdapter(coopAdapter);
            listRV.setHasFixedSize(true);
        }

        @Override
        public void onError(Throwable e) {
            refreshLayout.setVisibility(View.GONE);
            statusLLOne.setVisibility(View.GONE);
            statusLLTwo.setVisibility(View.GONE);
            coopListener.setScanVisibility(false);
            ToastUtil.showShortToast(context, getResources().getString(R.string.txt_server_error));
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

    public ProjectBean getProject() {
        return projectBean;
    }

    public void setProject(ProjectBean projectBean) {
        this.projectBean = projectBean;
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
