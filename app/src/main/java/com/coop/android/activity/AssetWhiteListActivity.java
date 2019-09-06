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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.adapter.WhiteListAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.WhiteListResponseBean;
import com.coop.android.model.WhiteMumberBean;
import com.coop.android.utils.GlideUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.umeng.analytics.MobclickAgent;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class AssetWhiteListActivity extends CBaseActivity implements View.OnClickListener, AlertDialog.OnDialogButtonClickListener {
    private static final String TAG = "AssetWhiteListActivity";
    protected Toolbar toolBar;
    private String assetId;
    private SearchView mSearchView;
    private WhiteListAdapter whiteListAdapter;
    private RecyclerView mWhiteList;
    private TextView whiteListUsedTV, projectMemberNameTV, projectMemberPhoneTV, addTV;
    private LinearLayout searchMumberLL;
    private CircleImageView memberHeaderImg;
    private String inveRoleId;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String assetId) {
        return new Intent(context, AssetWhiteListActivity.class).putExtra("assetId", assetId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_white_list);
        StatusBarUtils.setStatusBarColorDefault(this);
        intent = getIntent();
        assetId = intent.getStringExtra("assetId");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_asset_white_list));
        toolBar = findViewById(R.id.toolbar_img);
        mSearchView = findViewById(R.id.mSearchView);
        mWhiteList = findViewById(R.id.mWhiteList);
        whiteListUsedTV = findViewById(R.id.whiteListUsedTV);
        projectMemberNameTV = findViewById(R.id.projectMemberNameTV);
        projectMemberPhoneTV = findViewById(R.id.projectMemberPhoneTV);
        addTV = findViewById(R.id.addTV);
        searchMumberLL = findViewById(R.id.searchMumberLL);
        memberHeaderImg = findViewById(R.id.memberHeaderImg);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mWhiteList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initData() {
        if (StringUtil.isEmpty(assetId))
            return;
        HttpPostApi httpPostApi = new HttpPostApi(assetWhiteListOnNextListener, context, HttpPostApi.ASSET_WHITE_LIST, true);
        httpPostApi.setAssetId(assetId);
        httpPostApi.setPhoneNumber("");
        HttpManager.getInstance().doHttpDeal(httpPostApi);
    }

    @Override
    public void initEvent() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!StringUtil.isPhone(query)) {
                    new AlertDialog(mContext, "提示", "请输入正确的手机号码",
                            false, 0, AssetWhiteListActivity.this).show();
                    return false;
                }
                HttpPostApi httpPostApi = new HttpPostApi(assetWhiteSearchOnNextListener, context, HttpPostApi.ASSET_WHITE_LIST, true);
                httpPostApi.setAssetId(assetId);
                httpPostApi.setPhoneNumber(query);
                HttpManager.getInstance().doHttpDeal(httpPostApi);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchMumberLL.setVisibility(View.GONE);
                mWhiteList.setVisibility(View.VISIBLE);
                return false;
            }
        });
        addTV.setOnClickListener(this);
    }

    HttpOnNextListener assetWhiteSearchOnNextListener = new HttpOnNextListener<WhiteListResponseBean>() {
        @Override
        public void onNext(WhiteListResponseBean whiteListResponseBean, int code) {
            searchMumberLL.setVisibility(View.GONE);
            mWhiteList.setVisibility(View.VISIBLE);
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
            if (whiteListResponseBean.getWhitelist() == null || whiteListResponseBean.getWhitelist().size() == 0) {
                return;
            }
            searchMumberLL.setVisibility(View.VISIBLE);
            mWhiteList.setVisibility(View.GONE);
            WhiteMumberBean bean = whiteListResponseBean.getWhitelist().get(0);
            inveRoleId = bean.getInve_role_id();
            projectMemberNameTV.setText(bean.getNick_name());
            projectMemberPhoneTV.setText(bean.getMobile_no());
            String photourl;
            if (StringUtil.isEmpty(bean.getAvatar()))
                photourl = "";
            else
                photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + bean.getAvatar();
            GlideUtils.loadImage(context, photourl, memberHeaderImg, R.mipmap.default_user_header);

        }

        @Override
        public void onError(Throwable e) {
            searchMumberLL.setVisibility(View.GONE);
            mWhiteList.setVisibility(View.VISIBLE);
            ToastUtil.showShortToast(context, e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    HttpOnNextListener assetWhiteListOnNextListener = new HttpOnNextListener<WhiteListResponseBean>() {
        @Override
        public void onNext(WhiteListResponseBean whiteListResponseBean, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
            whiteListAdapter = new WhiteListAdapter(context, whiteListResponseBean.getWhitelist());
            mWhiteList.setAdapter(whiteListAdapter);
            mWhiteList.setHasFixedSize(true);
            whiteListUsedTV.setText(whiteListResponseBean.getWhitelist_use() + "/" + whiteListResponseBean.getWhitelist_max());
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(context, e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addTV:
                if (TextUtils.isEmpty(inveRoleId))
                    return;
                HttpPostApi httpPostApi = new HttpPostApi(addAssetWhiteOnNextListener, context, HttpPostApi.ADD_ASSET_WHITE, true);
                httpPostApi.setInveRoleId(inveRoleId);
                httpPostApi.setAssetId(assetId);
                HttpManager.getInstance().doHttpDeal(httpPostApi);
                break;
            default:
                break;
        }
    }

    HttpOnNextListener addAssetWhiteOnNextListener = new HttpOnNextListener<String>() {
        @Override
        public void onNext(String string, int code) {
            searchMumberLL.setVisibility(View.GONE);
            mWhiteList.setVisibility(View.VISIBLE);
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
            if (code == 501) {
                ToastUtil.showShortToast(context, "名额已达上限!");
                return;
            }
            if (code == 502) {
                ToastUtil.showShortToast(context, "用户已存在!");
                return;
            }
            ToastUtil.showShortToast(context, "白名单添加成功!");
            initData();
        }

        @Override
        public void onError(Throwable e) {
            searchMumberLL.setVisibility(View.GONE);
            mWhiteList.setVisibility(View.VISIBLE);
            ToastUtil.showShortToast(context, e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("交易名单页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("交易名单页面");
        MobclickAgent.onPause(this);
    }
}
