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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.adapter.EnterTeamAdapter;
import com.coop.android.adapter.PartnerTeamAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.EnterTeamBean;
import com.coop.android.model.EnterTransBean;
import com.coop.android.model.ProjectTeamListResponseBean;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class AssetTeamListActivity extends CBaseActivity {
    private static final String TAG = "AssetTeamListActivity";
    protected Toolbar toolBar;
    private RecyclerView mEnterList, mPartnerList;
    private EnterTeamAdapter enterTeamAdapter;
    private PartnerTeamAdapter partnerTeamAdapter;
    private String assetId;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String assetId) {
        return new Intent(context, AssetTeamListActivity.class).putExtra("assetId", assetId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_team_list);
        StatusBarUtils.setStatusBarColorDefault(this);
        intent = getIntent();
        assetId = intent.getStringExtra("assetId");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_asset_team_list));
        toolBar = findViewById(R.id.toolbar_img);
        mEnterList = findViewById(R.id.mEnterList);
        mPartnerList = findViewById(R.id.mPartnerList);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RecyclerView.LayoutManager enterLayoutManager =
                new GridLayoutManager(mContext, 4);
        RecyclerView.LayoutManager partnerLayoutManager =
                new GridLayoutManager(mContext, 4);
        mEnterList.setLayoutManager(enterLayoutManager);
        mPartnerList.setLayoutManager(partnerLayoutManager);
    }

    @Override
    public void initData() {
        if (StringUtil.isEmpty(assetId))
            return;
        HttpPostApi httpPostApi = new HttpPostApi(projectTeamListOnNextListener, context, HttpPostApi.PROJECT_TEAM_LIST, true);
        httpPostApi.setAssetId(assetId);
        HttpManager.getInstance().doHttpDeal(httpPostApi);
    }

    @Override
    public void initEvent() {

    }

    HttpOnNextListener projectTeamListOnNextListener = new HttpOnNextListener<ProjectTeamListResponseBean>() {
        @Override
        public void onNext(ProjectTeamListResponseBean projectTeamListResponseBean, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
            List<EnterTeamBean> enterTeamList = projectTeamListResponseBean.getEntrTeamList();
            enterTeamAdapter = new EnterTeamAdapter(context, enterTeamList);
            partnerTeamAdapter = new PartnerTeamAdapter(context, projectTeamListResponseBean.getInveTeamList());
            mEnterList.setAdapter(enterTeamAdapter);
            mPartnerList.setAdapter(partnerTeamAdapter);
            mEnterList.setHasFixedSize(true);
            mPartnerList.setHasFixedSize(true);
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
        MobclickAgent.onPageStart("团队成员页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("团队成员页面");
        MobclickAgent.onPause(this);
    }
}
