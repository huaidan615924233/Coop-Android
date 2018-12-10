package com.coop.android.activity;

import android.app.Activity;
import android.os.Bundle;
import com.coop.android.R;


/**
 * 启动页面
 * 在获取用户信息之后才会进入下一个页面
 */
public class WelcomeActivity extends Activity{
    public static final String EXTRA_SYSTEM_INFO = "WelcomeActivity";
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    /**
     * @return true 首次启动，false 非首次启动
     */
    protected boolean isFirstRun(){
        return true;
    };

    /**
     * 进入到启动页面
     */
    private void enterHomeActivity() {

    }


}