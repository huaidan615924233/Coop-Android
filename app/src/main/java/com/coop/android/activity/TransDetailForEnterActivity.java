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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.adapter.AgreementAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.AgreementBean;
import com.coop.android.model.TransDetailBean;
import com.coop.android.model.TransVoucherBean;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CommonPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class TransDetailForEnterActivity extends CBaseActivity implements View.OnClickListener, CommonPopupWindow.ViewInterface {
    private static final String TAG = "TransDetailForEnterActivity";
    protected Toolbar toolBar;
    private TextView tranTokenTV, hasTokenTV, blockChinaHashTV, heightTV, dateTV;
    private String transNo, account;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView listRV;
    private AgreementAdapter adapter;
    private CommonPopupWindow popupWindow;
    private TextView checkBlockChinaBtn, tokenLabelTV;
    private String hashStr;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String transNo, String account) {
        return new Intent(context, TransDetailForEnterActivity.class).putExtra("transNo", transNo).putExtra("account", account);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_info);
        intent = getIntent();
        transNo = intent.getStringExtra("transNo");
        account = intent.getStringExtra("account");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_trans_detail));
        checkBlockChinaBtn = findViewById(R.id.checkBlockChinaBtn);
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        tranTokenTV = findViewById(R.id.tranTokenTV);
        hasTokenTV = findViewById(R.id.hasTokenTV);
        blockChinaHashTV = findViewById(R.id.blockChinaHashTV);
        tokenLabelTV = findViewById(R.id.tokenLabelTV);
        tokenLabelTV.setText("转出通证");
        heightTV = findViewById(R.id.heightTV);
        dateTV = findViewById(R.id.dateTV);
        listRV = findViewById(R.id.listRV);
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

    HttpOnNextListener transDetailOnNextListener = new HttpOnNextListener<TransDetailBean>() {
        @Override
        public void onNext(TransDetailBean transDetailBean, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            tranTokenTV.setText(String.valueOf(transDetailBean.getTokenNum()));
            if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole()))
                hasTokenTV.setText(String.valueOf(transDetailBean.getEntrBalanceAmount()));
            else
                hasTokenTV.setText(String.valueOf(transDetailBean.getInveBalanceAmount()));
            blockChinaHashTV.setText(transDetailBean.getTransHash());
            hashStr = transDetailBean.getTransHash();
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
            adapter = new AgreementAdapter(mContext, agreementBeans, transVoucherBean);
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
    public void initEvent() {
        checkBlockChinaBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.checkBlockChinaBtn:
                showAll(v);
                break;
            default:
                break;
        }
    }

    //全屏弹出
    public void showAll(View view) {
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
                Button nextBtn = view.findViewById(R.id.nextBtn);
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing())
                            popupWindow.dismiss();
                        startActivity(BrowserActivity.createIntent(mContext));
                    }
                });
                break;
            default:
                break;
        }
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
