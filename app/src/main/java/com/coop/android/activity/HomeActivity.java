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

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.coop.android.AppConfigs;
import com.coop.android.BuildConfig;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.fragment.CoopFragment;
import com.coop.android.fragment.PartnerFragment;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.interfaces.CoopListener;
import com.coop.android.model.TransBean;
import com.coop.android.utils.AppStatus;
import com.coop.android.utils.AppStatusManager;
import com.coop.android.utils.CheckPermissionUtils;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.GlideUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.coop.android.view.DrawableButton;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.swichlayout.SwitchLayout;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;


public class HomeActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, CoopListener, AlertDialog.OnDialogButtonClickListener {
    private static final String TAG = "HomeActivity";
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    private DrawableButton scanBtn, qrcodeBtn;
    private TextView helpTxt;
    private TextView homeUserName;
    private CircleImageView homeHeaderImg;
    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    private FragmentManager fragmentManager;
    private FragmentTransaction transition;
    private CoopFragment coopFragment;
    private PartnerFragment partnerFragment;
    public static boolean isChooseUser = false;

    /**
     * 进入主页
     *
     * @return
     */
    public static void newInstance(Context context, boolean isChooseUser) {
        HomeActivity.isChooseUser = isChooseUser;
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        SwitchLayout.RotateHorizontal(this, false, null);
        if (isChooseUser) {
            isChooseUser = false;
            SwitchLayout.get3DRotateFromRight(this, false, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppStatusManager.getInstance().getAppStatus() == AppStatus.STATUS_RECYCLE) {
            //跳到闪屏页
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
//        if(NavigationBarUtil.hasNavigationBar(this)){
//            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
//        }
        helpTxt = findViewById(R.id.txtHelp);
        scanBtn = findViewById(R.id.btnScan);
        qrcodeBtn = findViewById(R.id.btnQrcode);
        homeUserName = findViewById(R.id.homeUserName);
        homeHeaderImg = findViewById(R.id.homeHeaderImg);
        //初始化权限
        CheckPermissionUtils.initPermission(context);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initData() {
        homeUserName.setText(UserConfigs.getInstance().getNickName());
        String photourl;
        if (StringUtil.isEmpty(UserConfigs.getInstance().getAvatar()))
            photourl = "";
        else
            photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + UserConfigs.getInstance().getAvatar();
        GlideUtils.loadImage(mContext, photourl, homeHeaderImg, R.mipmap.default_home_header);
        if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            scanBtn.setVisibility(View.VISIBLE);
            qrcodeBtn.setVisibility(View.GONE);
            coopFragment = CoopFragment.createInstance();
            coopFragment.setCoopListener(this);
            transition = fragmentManager.beginTransaction();
            transition.replace(R.id.container, coopFragment).commitAllowingStateLoss();
            transition.show(coopFragment);
        } else {
            qrcodeBtn.setVisibility(View.VISIBLE);
            scanBtn.setVisibility(View.GONE);
            partnerFragment = PartnerFragment.createInstance();
            transition = fragmentManager.beginTransaction();
            transition.replace(R.id.container, partnerFragment).commitAllowingStateLoss();
            transition.show(partnerFragment);
        }
    }

    @Override
    public void initEvent() {
//        helpTxt.setOnClickListener(HomeActivity.this);
        scanBtn.setOnClickListener(HomeActivity.this);
        qrcodeBtn.setOnClickListener(HomeActivity.this);
        homeHeaderImg.setOnClickListener(HomeActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnQrcode) {
            startActivity(AddTransactionDescActivity.createIntent(mContext));
        } else if (view.getId() == R.id.btnScan) {
            cameraTask();
        } else if (view.getId() == R.id.txtHelp) {
            startActivity(AboutUsActivity.createIntent(mContext));
        } else if (view.getId() == R.id.homeHeaderImg) {
            startActivity(PersionalActivity.createIntent(mContext));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Log.e(TAG, "解析结果:" + result);
                    TransBean transBean = new TransBean();
                    try {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (StringUtil.isEmpty(jsonObject.getString("desc")) || StringUtil.isEmpty(jsonObject.getString("userId"))
                                || StringUtil.isEmpty(jsonObject.getString("userName")) || StringUtil.isEmpty(jsonObject.getString("name"))
                                || StringUtil.isEmpty(jsonObject.getString("registrationId"))) {
                            ToastUtil.showShortToast(mContext, "解析二维码失败!");
                            return;
                        }
                        transBean.setProjectName(jsonObject.getString("userName"));
                        transBean.setInveCustId(jsonObject.getString("userId"));
                        transBean.setInveRemark(jsonObject.getString("desc"));
                        transBean.setName(jsonObject.getString("name"));
                        transBean.setRegistrationId(jsonObject.getString("registrationId"));
                    } catch (Exception e) {
                        ToastUtil.showShortToast(mContext, "解析二维码失败!");
                        return;
                    }
                    if (coopFragment == null || coopFragment.getProject() == null) {
                        ToastUtil.showShortToast(mContext, "当前无项目，不支持扫码！");
                        return;
                    }
                    transBean.setProjectId(coopFragment.getProject().getId());
                    transBean.setProjectToken(coopFragment.getProject().getList() == null || coopFragment.getProject().getList().size() == 0 ?
                            coopFragment.getProject().getProjectToken() : coopFragment.getProject().getList().get(0).getBalanceAmount());
                    transBean.setStockPercent(String.format("%.0f", Double.parseDouble(coopFragment.getProject().getStockPercent()) * 100) + "%");
                    transBean.setProjectTokenPrice(coopFragment.getProject().getProjectPercent());
                    startActivity(PayTokenActivity.createIntent(mContext, transBean));
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showShortToast(mContext, "解析二维码失败!");
                }
            }
        }
    }

    /**
     * EsayPermissions接管权限处理逻辑
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(ConstantUtil.PERMISSIONS_REQUEST_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            if (StringUtil.isEmpty(UserConfigs.getInstance().getSalt())) {
                new AlertDialog(mContext, "提示", "还没有设置支付密码，是否去设置？", true, 0, HomeActivity.this).show();
                return;
            }
            //扫码事件统计
            MobclickAgent.onEvent(mContext, "onClick", "扫码");
            startActivityForResult(ScanActivity.createIntent(mContext), REQUEST_CODE);
            overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求camera权限",
                    ConstantUtil.PERMISSIONS_REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e(TAG, "执行onPermissionsGranted()...");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e(TAG, "执行onPermissionsDenied()...");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(ConstantUtil.PERMISSIONS_REQUEST_CAMERA_PERM)
                    .build()
                    .show();
        }
    }

    @Override
    public void setScanVisibility(boolean isShow) {
        scanBtn.setVisibility(isShow == true ? View.VISIBLE : View.GONE);
    }

    /**
     * 双击返回键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtil.showShortToast(HomeActivity.this, "再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (isPositive) {
            toActivity(UpdatePasswordActivity.createIntent(mContext, ConstantUtil.SETPASSWORD));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); //统计时长
    }
}
