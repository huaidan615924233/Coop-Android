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


import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.BuildConfig;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.activity.SettingActivity;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.GlideUtils;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CircleImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class HomeSideFragment extends BaseFragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    public static final String TAG = "HomeSideFragment";
    private TextView versionNumberTV, userNameText, wchatIDTV, phoneNumberTV;
    private CircleImageView userHeaderImg;
    private LinearLayout sideActiveLL, sideCallmeLL, sideWChatLL, sideSettingLL;

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static HomeSideFragment createInstance() {
        return new HomeSideFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home_side);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void initView() {
        versionNumberTV = findViewById(R.id.versionNumberTV);
        userHeaderImg = findViewById(R.id.userHeaderImg);
        userNameText = findViewById(R.id.userNameText);
        sideActiveLL = findViewById(R.id.sideActiveLL);
        sideCallmeLL = findViewById(R.id.sideCallmeLL);
        sideWChatLL = findViewById(R.id.sideWChatLL);
        sideSettingLL = findViewById(R.id.sideSettingLL);
        wchatIDTV = findViewById(R.id.wchatIDTV);
        phoneNumberTV = findViewById(R.id.phoneNumberTV);
    }

    @Override
    public void initData() {
        versionNumberTV.setText("版本号: " + BuildConfig.VERSION_NAME);
        if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            userNameText.setText(UserConfigs.getInstance().getNickName());
//            userNameText.setText(UserConfigs.getInstance().getNickName() + " [ 创业者 ] ");
        } else {
            userNameText.setText(UserConfigs.getInstance().getNickName());
//            userNameText.setText(UserConfigs.getInstance().getNickName() + " [ 协作者 ] ");
        }
        String photourl;
        if (StringUtil.isEmpty(UserConfigs.getInstance().getAvatar()))
            photourl = "";
        else
            photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + UserConfigs.getInstance().getAvatar();
        GlideUtils.loadImage(context, photourl, userHeaderImg,
                ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())
                        ? R.mipmap.default_enter_user_header : R.mipmap.default_user_header);
    }

    @Override
    public void initEvent() {
        sideActiveLL.setOnClickListener(this);
        sideCallmeLL.setOnClickListener(this);
        sideWChatLL.setOnClickListener(this);
        sideSettingLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.sideActiveLL:
                break;
            case R.id.sideCallmeLL:
                callPhoneTask();
                break;
            case R.id.sideWChatLL:
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, wchatIDTV.getText()));
                ToastUtil.showShortToast(context, "微信已复制到剪贴板");
                break;
            case R.id.sideSettingLL:
                toActivity(SettingActivity.createIntent(context));
                break;
            default:
                break;
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

    @AfterPermissionGranted(ConstantUtil.PERMISSIONS_REQUEST_CALL_PHONE_PERM)
    public void callPhoneTask() {
        if (EasyPermissions.hasPermissions(context, Manifest.permission.CALL_PHONE)) {
            // Have permission, do the thing!
            CommonUtil.call(context, phoneNumberTV.getText().toString());
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求打电话权限",
                    ConstantUtil.PERMISSIONS_REQUEST_CALL_PHONE_PERM, Manifest.permission.CALL_PHONE);
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
                if (perms.get(i).equals(Manifest.permission.CALL_PHONE))
                    new AppSettingsDialog.Builder(this, "当前App需要申请打电话权限,需要打开设置页面么?")
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("侧边栏页面");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("侧边栏页面");
    }

}
