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


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.activity.LoginActivity;
import com.coop.android.activity.TransDetailActivity;
import com.coop.android.adapter.AgreementAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.AgreementBean;
import com.coop.android.model.TransDetailBean;
import com.coop.android.model.TransVoucherBean;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class TransInfoFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "TransInfoFragment";
    private SmartRefreshLayout refreshLayout;
    private RecyclerView listRV;
    private AgreementAdapter adapter;
    private TextView checkBlockChinaBtn;
    private TextView tranTokenTV, hasTokenTV, blockChinaHashTV, heightTV, dateTV;
    private String transNo, account;

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static TransInfoFragment createInstance() {
        return new TransInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_trans_info);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void initView() {
        transNo = ((TransDetailActivity) context).getTransNo();
        account = ((TransDetailActivity) context).getAccount();
        tranTokenTV = findViewById(R.id.tranTokenTV);
        hasTokenTV = findViewById(R.id.hasTokenTV);
        blockChinaHashTV = findViewById(R.id.blockChinaHashTV);
        heightTV = findViewById(R.id.heightTV);
        dateTV = findViewById(R.id.dateTV);
        listRV = findViewById(R.id.listRV);
        checkBlockChinaBtn = findViewById(R.id.checkBlockChinaBtn);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);//取消内容不满一页时开启上拉加载功能
        refreshLayout.setEnableLoadmore(false); //禁用下拉加载
        refreshLayout.setEnableRefresh(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        listRV.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initData() {
        if (StringUtil.isEmpty(transNo) || StringUtil.isEmpty(account))
            return;
        HttpPostApi httpPostApi = new HttpPostApi(transDetailOnNextListener, context, HttpPostApi.TRANS_DETAIL, true);
        httpPostApi.setAccount(account);
        httpPostApi.setTransNo(transNo);
        httpPostApi.setRoleType(UserConfigs.getInstance().getLastLoginRole());
        HttpManager.getInstance().doHttpDeal(httpPostApi);
    }

    @Override
    public void initEvent() {
        checkBlockChinaBtn.setOnClickListener(this);
    }

    HttpOnNextListener transDetailOnNextListener = new HttpOnNextListener<TransDetailBean>() {
        @Override
        public void onNext(TransDetailBean transDetailBean, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
            tranTokenTV.setText(String.valueOf(transDetailBean.getTokenNum()));
            if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole()))
                hasTokenTV.setText(String.valueOf(transDetailBean.getEntrBalanceAmount()));
            else
                hasTokenTV.setText(String.valueOf(transDetailBean.getInveBalanceAmount()));
            blockChinaHashTV.setText(transDetailBean.getTransHash());
            heightTV.setText(transDetailBean.getTransHeight());

            String projectTime = transDetailBean.getTransTimestamp();
            if (!StringUtil.isEmpty(projectTime)) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectTime);
                    String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                    dateTV.setText(now);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            List<AgreementBean> agreementBeans = new ArrayList<>();
            AgreementBean agreementBean = new AgreementBean();
            agreementBean.setFileName("查看交易凭证");
            agreementBean.setUrl("");
            agreementBeans.add(agreementBean);
            agreementBeans.addAll(transDetailBean.getFile_list());
            TransVoucherBean transVoucherBean = new TransVoucherBean();
            transVoucherBean.setTransNum(String.valueOf(transDetailBean.getTokenNum()));
            // TODO 添加TokenName
            transVoucherBean.setTokenName("(wo)");
            transVoucherBean.setTransDate(dateTV.getText().toString());
            transVoucherBean.setVoucherName(transDetailBean.getInveName());
            transVoucherBean.setEntrRealName(transDetailBean.getEntrRealName());
            transVoucherBean.setInveRealName(transDetailBean.getInveRealName());
            adapter = new AgreementAdapter(context, agreementBeans, transVoucherBean);
            listRV.setAdapter(adapter);
            listRV.setHasFixedSize(true);
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(context, e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("交易详情页面");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("交易详情页面");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.checkBlockChinaBtn:
                ((TransDetailActivity) context).showAll(v, blockChinaHashTV.getText().toString());
                break;
            default:
                break;
        }
    }
}
