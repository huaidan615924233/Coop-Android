package com.coop.android;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.coop.android.activity.HomeActivity;
import com.coop.android.utils.AppStatus;
import com.coop.android.utils.AppStatusManager;

import java.lang.reflect.Method;

import zuo.biao.library.base.BaseActivity;

/**
 * Created by MR-Z on 2019/1/3.
 */
public abstract class CBaseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppStatusManager.getInstance().getAppStatus() == AppStatus.STATUS_RECYCLE) {
            //跳到HomeActivity,让HomeActivity也finish掉
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
    }
}
