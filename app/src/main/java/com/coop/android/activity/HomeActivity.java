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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coop.android.AppConfigs;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.fragment.CoopFragment;
import com.coop.android.fragment.HomeSideFragment;
import com.coop.android.fragment.PartnerFragment;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.interfaces.CoopListener;
import com.coop.android.model.LoginResponseBean;
import com.coop.android.model.TransBean;
import com.coop.android.model.UserInfo;
import com.coop.android.utils.AppStatus;
import com.coop.android.utils.AppStatusManager;
import com.coop.android.utils.CheckPermissionUtils;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.GlideUtils;
import com.coop.android.utils.SharedPreferencesUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.coop.android.view.CommonPopupWindow;
import com.coop.android.view.DrawableButton;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;


public class HomeActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, CoopListener,
        AlertDialog.OnDialogButtonClickListener, CommonPopupWindow.ViewInterface {
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

    //侧边栏相关
    protected Toolbar mToolBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private HomeSideFragment homeSideFragment;
    private LinearLayout chooseLL;
    private TextView homeChooseTV;
    private CommonPopupWindow popupWindow;
    private String roleType;


    /**
     * 进入主页
     *
     * @return
     */
    public static void newInstance(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
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

        //侧边栏相关
        mToolBar = findViewById(R.id.toolbar_img);
        mToolBar.setNavigationIcon(R.mipmap.home_side_icon);
        drawerLayout = findViewById(R.id.drawerLayout);
        chooseLL = findViewById(R.id.chooseLL);
        homeChooseTV = findViewById(R.id.homeChooseTV);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolBar, 0, 0);
        // 添加此句，toolbar左上角显示开启侧边栏图标
//        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);
        // 禁用手势
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void initData() {
        homeUserName.setText(UserConfigs.getInstance().getNickName());
        String photourl;
        if (StringUtil.isEmpty(UserConfigs.getInstance().getAvatar()))
            photourl = "";
        else
            photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + UserConfigs.getInstance().getAvatar();
        GlideUtils.loadImage(mContext, photourl, homeHeaderImg,
                ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())
                        ? R.mipmap.default_enter_user_header : R.mipmap.default_user_header);

        homeSideFragment = HomeSideFragment.createInstance();
        if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            scanBtn.setVisibility(View.VISIBLE);
            qrcodeBtn.setVisibility(View.GONE);
            coopFragment = CoopFragment.createInstance();
            coopFragment.setCoopListener(this);
            transition = fragmentManager.beginTransaction();
            transition.replace(R.id.sideContainer, homeSideFragment);
            transition.replace(R.id.container, coopFragment).commitAllowingStateLoss();
            transition.show(coopFragment);
            homeChooseTV.setText("创业者");
        } else {
            qrcodeBtn.setVisibility(View.VISIBLE);
            scanBtn.setVisibility(View.GONE);
            partnerFragment = PartnerFragment.createInstance();
            transition = fragmentManager.beginTransaction();
            transition.replace(R.id.sideContainer, homeSideFragment);
            transition.replace(R.id.container, partnerFragment).commitAllowingStateLoss();
            transition.show(partnerFragment);
            homeChooseTV.setText("协作者");
        }
    }

    @Override
    public void initEvent() {
//        helpTxt.setOnClickListener(HomeActivity.this);
        scanBtn.setOnClickListener(HomeActivity.this);
        qrcodeBtn.setOnClickListener(HomeActivity.this);
        chooseLL.setOnClickListener(this);
        //侧边栏相关
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnQrcode) {
            startActivity(AddTransactionDescActivity.createIntent(mContext));
        } else if (view.getId() == R.id.btnScan) {
            cameraTask();
        } else if (view.getId() == R.id.txtHelp) {
            startActivity(AboutUsActivity.createIntent(mContext));
        } else if (view.getId() == R.id.chooseLL) {
            showAsDropDown(view);
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
                    if (coopFragment == null || coopFragment.getAssetBean() == null) {
                        ToastUtil.showShortToast(mContext, "当前无资产，不支持扫码！");
                        return;
                    }
                    transBean.setProjectId(coopFragment.getAssetBean().getId());
                    transBean.setProjectToken(coopFragment.getAssetBean().getProject_token());
                    transBean.setAsset_name(coopFragment.getAssetBean().getAsset_name());
                    transBean.setStockPercent(String.format("%.0f", Double.parseDouble(coopFragment.getAssetBean().getStock_percent()) * 100) + "%");
                    transBean.setProjectTokenPrice(coopFragment.getAssetBean().getProject_amount());
                    startActivity(PayTokenActivity.createIntent(mContext, transBean));
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showShortToast(mContext, "解析二维码失败!");
                }
            }
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_home_choose:
                TextView popEnterTV = view.findViewById(R.id.popEnterTV);
                TextView popPartnerTV = view.findViewById(R.id.popPartnerTV);
                popEnterTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        roleType = ConstantUtil.ENTERIDEN;
                        changeRole(roleType);
                    }
                });
                popPartnerTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        roleType = ConstantUtil.PARTNERIDEN;
                        changeRole(roleType);
                    }
                });
                break;
            default:
                break;
        }
    }

    //底部弹出
    public void showAsDropDown(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View popView = LayoutInflater.from(this).inflate(R.layout.popup_home_choose, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(popView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_home_choose)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, popView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
                .setOutsideTouchable(true)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAsDropDown(view, 0, 0);
    }

    private void changeRole(String roleType) {
        if (roleType.equals(UserConfigs.getInstance().getLastLoginRole()))
            return;
        HttpPostApi postEntity = new HttpPostApi(changeRoleOnNextListener, this, HttpPostApi.CHANGE_ROLE, false);
        postEntity.setRoleType(roleType);
        postEntity.setUserId(UserConfigs.getInstance().getId());
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    HttpOnNextListener changeRoleOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            if (code == 501 || code == 502) {
                startActivity(LoginChooseActivity.createIntent(mContext));
                return;
            }
            String userInfo = SharedPreferencesUtils.getUserInfo(mContext);
            LoginResponseBean user = JSON.parseObject(userInfo, LoginResponseBean.class);
            UserInfo userInfoTemp = user.getUser();
            userInfoTemp.setLastLoginRole(roleType);
            user.setUser(userInfoTemp);
            userInfo = JSON.toJSONString(user);
            SharedPreferencesUtils.setUserInfo(mContext, userInfo);
            UserConfigs.loadUserInfo(userInfo);
//            ToastUtil.showShortToast(getApplicationContext(), "角色切换成功!");
//            finish();
//            HomeActivity.newInstance(mContext);
            initData();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(getApplicationContext(), e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

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
            for (int i = 0; i < perms.size(); i++) {
                if (perms.get(i).equals(Manifest.permission.CAMERA))
                    new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
                            .setTitle("权限申请")
                            .setPositiveButton("确认")
                            .setNegativeButton("取消", null /* click listener */)
                            .setRequestCode(ConstantUtil.PERMISSIONS_REQUEST_CAMERA_PERM)
                            .build()
                            .show();
                else if (perms.get(i).equals(Manifest.permission.CALL_PHONE))
                    new AppSettingsDialog.Builder(this, "当前App需要申请打电话权限,需要打开设置页面么?")
                            .setTitle("权限申请")
                            .setPositiveButton("确认")
                            .setNegativeButton("取消", null /* click listener */)
                            .setRequestCode(ConstantUtil.PERMISSIONS_REQUEST_CALL_PHONE_PERM)
                            .build()
                            .show();
                else if (perms.get(i).equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    new AppSettingsDialog.Builder(this, "当前App需要申请读写权限,需要打开设置页面么?")
                            .setTitle("权限申请")
                            .setPositiveButton("确认")
                            .setNegativeButton("取消", null /* click listener */)
                            .setRequestCode(ConstantUtil.PERMISSIONS_REQUEST_CALL_PHONE_PERM)
                            .build()
                            .show();
            }
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
