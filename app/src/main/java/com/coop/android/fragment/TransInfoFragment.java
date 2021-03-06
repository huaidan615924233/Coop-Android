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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.activity.LoginActivity;
import com.coop.android.activity.TransBlockInfoActivity;
import com.coop.android.activity.TransDetailActivity;
import com.coop.android.activity.TransVoucherActivity;
import com.coop.android.adapter.AgreementAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.AgreementBean;
import com.coop.android.model.AssetBean;
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
    private TextView tranTokenTV, hasTokenTV, blockChinaHashTV, heightTV, dateTV, assetTypeTV;
    private String transNo;
    private LinearLayout transAgreeLL, transVoucherLL,getTokenCountLL;
    private ImageView transAgreeRightIV;
    private boolean isExpand = false;
    private TransVoucherBean transVoucherBean;
    private TransDetailBean transDetail;

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
        transDetail = new TransDetailBean();
        transVoucherBean = new TransVoucherBean();
        transNo = ((TransDetailActivity) context).getTransNo();
        tranTokenTV = findViewById(R.id.tranTokenTV);
        hasTokenTV = findViewById(R.id.hasTokenTV);
        blockChinaHashTV = findViewById(R.id.blockChinaHashTV);
        assetTypeTV = findViewById(R.id.assetTypeTV);
        transVoucherLL = findViewById(R.id.transVoucherLL);
        getTokenCountLL = findViewById(R.id.getTokenCountLL);
        transAgreeLL = findViewById(R.id.transAgreeLL);
        transAgreeRightIV = findViewById(R.id.transAgreeRightIV);
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
        if (StringUtil.isEmpty(transNo))
            return;
        HttpPostApi httpPostApi = new HttpPostApi(transDetailOnNextListener, context, HttpPostApi.TRANS_DETAIL, true);
        httpPostApi.setTransNo(transNo);
        HttpManager.getInstance().doHttpDeal(httpPostApi);
    }

    @Override
    public void initEvent() {
        checkBlockChinaBtn.setOnClickListener(this);
        transVoucherLL.setOnClickListener(this);
        getTokenCountLL.setOnClickListener(this);
        transAgreeLL.setOnClickListener(this);
    }

    HttpOnNextListener transDetailOnNextListener = new HttpOnNextListener<TransDetailBean>() {
        @Override
        public void onNext(TransDetailBean transDetailBean, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
            transDetail = transDetailBean;
            tranTokenTV.setText(String.valueOf(transDetailBean.getToken_num()));
            if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole()))
                hasTokenTV.setText(String.valueOf(transDetailBean.getEntr_balance_amount()));
            else
                hasTokenTV.setText(String.valueOf(transDetailBean.getInve_balance_amount()));
            blockChinaHashTV.setText(transDetailBean.getTrans_hash());
            heightTV.setText(transDetailBean.getTrans_b_height());
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
            String projectTime = transDetailBean.getTrans_b_timestamp();
            if (!StringUtil.isEmpty(projectTime)) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectTime);
                    String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                    dateTV.setText(now);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            //交易协议相关
            transVoucherBean.setTransNum(String.valueOf(transDetailBean.getToken_num()));
            // TODO 添加TokenName
            transVoucherBean.setTokenName("(wo)");
            transVoucherBean.setTransDate(dateTV.getText().toString());
            transVoucherBean.setVoucherName(transDetailBean.getInve_nick_name());
            transVoucherBean.setEntrRealName(transDetailBean.getEntr_real_name());
            transVoucherBean.setInveRealName(transDetailBean.getInve_real_name());
            //合作协议
            List<AgreementBean> agreementBeans = new ArrayList<>();
            agreementBeans.addAll(transDetailBean.getFile_list());
            adapter = new AgreementAdapter(context, agreementBeans);
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
                toActivity(TransBlockInfoActivity.createIntent(context, transDetail));
                break;
            case R.id.transVoucherLL:
                toActivity(TransVoucherActivity.createIntent(context, transVoucherBean.getTokenName(), transVoucherBean.getVoucherName(),
                        transVoucherBean.getTransNum(), transVoucherBean.getTransDate(), transVoucherBean.getEntrRealName(), transVoucherBean.getInveRealName()));
                break;
            case R.id.getTokenCountLL:
                toActivity(TransVoucherActivity.createIntent(context, transVoucherBean.getTokenName(), transVoucherBean.getVoucherName(),
                        transVoucherBean.getTransNum(), transVoucherBean.getTransDate(), transVoucherBean.getEntrRealName(), transVoucherBean.getInveRealName()));
                break;
            case R.id.transAgreeLL:
                if (isExpand) {
                    transAgreeRightIV.setImageResource(R.mipmap.trans_go_right_icon);
                    listRV.setVisibility(View.GONE);
                    isExpand = false;
                } else {
                    transAgreeRightIV.setImageResource(R.mipmap.trans_go_bottom_icon);
                    listRV.setVisibility(View.VISIBLE);
                    isExpand = true;
                }
                break;
            default:
                break;
        }
    }
}
