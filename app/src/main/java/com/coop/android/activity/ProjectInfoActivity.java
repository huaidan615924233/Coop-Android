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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.adapter.AgreementAdapter;
import com.coop.android.adapter.AgreementProjectAdapter;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.AssetBean;
import com.coop.android.model.ProjectBean;
import com.coop.android.model.ProjectDetailResponseBean;
import com.coop.android.utils.GlideUtils;
import com.coop.android.utils.NumUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.coop.android.view.MultiImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class ProjectInfoActivity extends CBaseActivity implements View.OnClickListener {
    private static final String TAG = "ProjectInfoActivity";
    protected Toolbar toolBar;
    private String assetId;
    private TextView projectNameTV, projectTimeTV, tokenPerTV, projectTotalTV, tokenTotalTV, projectDescTV,
            projectMemberNameTV, expandTV, projectLabelTV, assetNameTV, assetTypeTV;
    private CircleImageView memberHeaderImg;
    private MultiImageView projectHeaderImg;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView listRV;
    private AgreementProjectAdapter adapter;
    private boolean projectInfoIsExpanded = false;
    private LinearLayout transAgreeLL;
    private ImageView transAgreeRightIV;
    private LinearLayout projectIsTransMembersLL, projectMemberLL;
    private boolean isExpand = false;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String assetId) {
        return new Intent(context, ProjectInfoActivity.class).putExtra("assetId", assetId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);
        StatusBarUtils.setStatusBarColorDefault(this);
        intent = getIntent();
        assetId = intent.getStringExtra("assetId");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getResources().getString(R.string.txt_title_project_detail));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        projectNameTV = findViewById(R.id.projectNameTV);
        projectTimeTV = findViewById(R.id.projectTimeTV);
        tokenPerTV = findViewById(R.id.tokenPerTV);
        projectTotalTV = findViewById(R.id.projectTotalTV);
        tokenTotalTV = findViewById(R.id.tokenTotalTV);
        projectDescTV = findViewById(R.id.projectDescTV);
        projectLabelTV = findViewById(R.id.projectLabelTV);
        projectMemberNameTV = findViewById(R.id.projectMemberNameTV);
        assetNameTV = findViewById(R.id.assetNameTV);
        assetTypeTV = findViewById(R.id.assetTypeTV);
        projectHeaderImg = findViewById(R.id.projectHeaderImg);
        memberHeaderImg = findViewById(R.id.memberHeaderImg);
        expandTV = findViewById(R.id.expandTV);
        refreshLayout = findViewById(R.id.refreshLayout);
        transAgreeLL = findViewById(R.id.transAgreeLL);
        transAgreeRightIV = findViewById(R.id.transAgreeRightIV);
        projectIsTransMembersLL = findViewById(R.id.projectIsTransMembersLL);
        projectMemberLL = findViewById(R.id.projectMemberLL);
        listRV = findViewById(R.id.listRV);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);//取消内容不满一页时开启上拉加载功能
        refreshLayout.setEnableLoadmore(false); //禁用下拉加载
        refreshLayout.setEnableRefresh(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        listRV.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initData() {
        if (StringUtil.isEmpty(assetId))
            return;
        HttpPostApi httpPostApi = new HttpPostApi(projectDetailOnNextListener, context, HttpPostApi.PROJECT_DETAIL, true);
        httpPostApi.setAssetId(assetId);
        HttpManager.getInstance().doHttpDeal(httpPostApi);

        // 2.Grid布局
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(mContext, 3, GridLayoutManager.HORIZONTAL, false);
    }

    @Override
    public void initEvent() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        transAgreeLL.setOnClickListener(this);
        projectIsTransMembersLL.setOnClickListener(this);
        projectMemberLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.transAgreeLL:
//                if (isExpand) {
//                    transAgreeRightIV.setImageResource(R.mipmap.project_go_right_icon);
//                    listRV.setVisibility(View.GONE);
//                    isExpand = false;
//                } else {
//                    transAgreeRightIV.setImageResource(R.mipmap.project_go_bottom_icon);
//                    listRV.setVisibility(View.VISIBLE);
//                    isExpand = true;
//                }
                break;
            case R.id.projectMemberLL:
                toActivity(AssetTeamListActivity.createIntent(context, assetId));
                break;
            case R.id.projectIsTransMembersLL:
                toActivity(AssetWhiteListActivity.createIntent(context, assetId));
                break;
            default:
                break;
        }
    }

    HttpOnNextListener projectDetailOnNextListener = new HttpOnNextListener<ProjectDetailResponseBean>() {
        @Override
        public void onNext(ProjectDetailResponseBean projectDetailResponseBean, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(context, "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(context, true));
                return;
            }
            adapter = new AgreementProjectAdapter(context, projectDetailResponseBean.getAsset().getFile_list());
            listRV.setAdapter(adapter);
            listRV.setHasFixedSize(true);

            String projectName = projectDetailResponseBean.getName();
            projectNameTV.setText(projectName == null ? " " : projectName);
            String projectTime = projectDetailResponseBean.getFound_time();
            if (!StringUtil.isEmpty(projectTime)) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectTime);
                    String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                    projectTimeTV.setText("成立于" + now);
                    DecimalFormat df = new DecimalFormat("##.######%");
                    tokenPerTV.setText(df.format(Double.parseDouble(projectDetailResponseBean.getAsset().getStock_percent())));
//                    tokenPerTV.setText(String.format("%.0f", Double.parseDouble(projectDetailResponseBean.getAsset().getStock_percent()) * 100) + "%");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            String photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + projectDetailResponseBean.getLogo();
            GlideUtils.loadImageRound(context, photourl, projectHeaderImg, R.mipmap.default_project_header);
            projectLabelTV.setText(projectDetailResponseBean.getType());
            assetNameTV.setText(projectDetailResponseBean.getAsset().getAsset_name());
            switch (projectDetailResponseBean.getAsset().getAsset_type()) {
                case AssetBean.ASSET_TYPE_1:
                    projectIsTransMembersLL.setVisibility(View.VISIBLE);
                    assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_1);
                    break;
                case AssetBean.ASSET_TYPE_2:
                    assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_2);
                    projectIsTransMembersLL.setVisibility(View.GONE);
                    break;
                case AssetBean.ASSET_TYPE_3:
                    projectIsTransMembersLL.setVisibility(View.VISIBLE);
                    assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_3);
                    break;
                case AssetBean.ASSET_TYPE_4:
                    projectIsTransMembersLL.setVisibility(View.VISIBLE);
                    assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_4);
                    break;
                default:
                    break;
            }
            projectTotalTV.setText(NumUtils.formatNum(projectDetailResponseBean.getAsset().getProject_amount(), false));
            tokenTotalTV.setText(projectDetailResponseBean.getAsset().getProject_token());
            if (projectDetailResponseBean.getEntr_team_list() != null && projectDetailResponseBean.getEntr_team_list().size() != 0) {
                if (!StringUtil.isEmail(projectDetailResponseBean.getEntr_team_list().get(0).getAvatar()))
                    GlideUtils.loadImage(context, AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA +
                            projectDetailResponseBean.getEntr_team_list().get(0).getAvatar(), memberHeaderImg, R.mipmap.default_enter_user_header);
                projectMemberNameTV.setText(projectDetailResponseBean.getEntr_team_list().get(0).getNick_name());
            }
            projectDescTV.setText(projectDetailResponseBean.getContent());
            if (projectDescTV.getLineCount() > 2) {
                projectDescTV.setMaxLines(2);// 收起
                expandTV.setVisibility(View.VISIBLE);
                expandTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (projectInfoIsExpanded) {
                            projectInfoIsExpanded = false;
                            projectDescTV.setMaxLines(2);// 收起
                            expandTV.setText("展开全部");
                            // 使用代码设置drawableleft
                            Drawable drawable = getResources().getDrawable(
                                    R.mipmap.more_btn);
                            // / 这一步必须要做,否则不会显示.
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                    drawable.getMinimumHeight());
                            expandTV.setCompoundDrawables(null, null, drawable, null);
                        } else {
                            projectInfoIsExpanded = true;
                            projectDescTV.setMaxLines(Integer.MAX_VALUE);// 展开
                            expandTV.setText("收起全部");
                            // 使用代码设置drawableleft
                            Drawable drawable = getResources().getDrawable(
                                    R.mipmap.more_top_btn);
                            // / 这一步必须要做,否则不会显示.
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                    drawable.getMinimumHeight());
                            expandTV.setCompoundDrawables(null, null, drawable, null);
                        }
                    }
                });
            }
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
        MobclickAgent.onPageStart("项目详情页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("项目详情页面");
        MobclickAgent.onPause(this);
    }
}
