package com.coop.android.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public final class CheckPermissionUtils {
    /**
     * 初始化权限事件
     */
    public static void initPermission(Activity context) {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(context);
        //申请权限
        boolean isMinSdkM = Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
        if (isMinSdkM || permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            ActivityCompat.requestPermissions(context, permissions, 100);
        }
    }

    private CheckPermissionUtils() {
    }

    //需要申请的权限
    private static String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE
    };

    //检测权限
    public static String[] checkPermission(Context context) {
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : permissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[data.size()]);
    }
}
