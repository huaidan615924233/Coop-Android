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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.ProjectBean;
import com.coop.android.model.ProjectDetailResponseBean;
import com.coop.android.utils.GlideUtils;
import com.coop.android.utils.NumUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class ProjectInfoActivity extends CBaseActivity {
    private static final String TAG = "ProjectInfoActivity";
    protected Toolbar toolBar;
    private String projectId;
    private TextView projectNameTV, projectTimeTV, tokenPerTV, projectTotalTV, tokenTotalTV, projectDescTV, projectMemberNameTV, expandTV, projectLabelTV;
    private CircleImageView projectHeaderImg;
    private SmartRefreshLayout refreshLayout;
    private boolean isExpanded = false;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String projectId) {
        return new Intent(context, ProjectInfoActivity.class).putExtra("projectId", projectId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);
        intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
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
        projectHeaderImg = findViewById(R.id.projectHeaderImg);
        expandTV = findViewById(R.id.expandTV);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);//取消内容不满一页时开启上拉加载功能
        refreshLayout.setEnableLoadmore(false); //禁用下拉加载
        refreshLayout.setEnableRefresh(false);
    }

    @Override
    public void initData() {
        if (StringUtil.isEmpty(projectId))
            return;
        HttpPostApi httpPostApi = new HttpPostApi(projectDetailOnNextListener, context, HttpPostApi.PROJECT_DETAIL, true);
        httpPostApi.setProjectId(projectId);
        HttpManager.getInstance().doHttpDeal(httpPostApi);
    }

    @Override
    public void initEvent() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    HttpOnNextListener projectDetailOnNextListener = new HttpOnNextListener<ProjectDetailResponseBean>() {
        @Override
        public void onNext(ProjectDetailResponseBean projectDetailResponseBean, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            final ProjectBean projectBean = projectDetailResponseBean.getProject();
            String projectName = projectBean.getName();
            projectNameTV.setText(projectName == null ? " " : projectName);
            String projectTime = projectBean.getFoundTime();
            if (!StringUtil.isEmpty(projectTime)) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectTime);
                    String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                    projectTimeTV.setText("成立于" + now);
                    tokenPerTV.setText(String.format("%.0f", Double.parseDouble(projectBean.getStockPercent()) * 100) + "%");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            String photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + projectBean.getAuthorizeUrl();
            GlideUtils.loadImage(mContext, photourl, projectHeaderImg, R.mipmap.default_home_header);
            projectLabelTV.setText(projectBean.getType());
            projectTotalTV.setText(NumUtils.formatNum(projectBean.getProjectAmount(), false));
            tokenTotalTV.setText(projectBean.getProjectToken());
            projectMemberNameTV.setText(projectBean.getNickName());
            projectDescTV.setText(projectBean.getRemark());
            if (projectDescTV.getLineCount() > 2) {
                projectDescTV.setMaxLines(2);// 收起
                expandTV.setVisibility(View.VISIBLE);
                expandTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isExpanded) {
                            isExpanded = false;
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
                            isExpanded = true;
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
            ToastUtil.showShortToast(context, getResources().getString(R.string.txt_server_error));
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
