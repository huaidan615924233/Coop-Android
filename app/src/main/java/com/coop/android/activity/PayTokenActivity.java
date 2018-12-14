package com.coop.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coop.android.R;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.view.CommonPopupWindow;
import com.coop.android.view.PwdEditText;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.CommonUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class PayTokenActivity extends BaseActivity implements View.OnClickListener, CommonPopupWindow.ViewInterface {
    private String projectName;
    protected Toolbar toolBar;
    private Button payConfimBtn;
    private CommonPopupWindow popupWindow;
    private TextView payDescTV;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String projectName) {
        return new Intent(context, PayTokenActivity.class).putExtra("projectName", projectName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_token);
        intent = getIntent();
        projectName = intent.getStringExtra("projectName");
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_pay_token) + projectName);
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                finish();
            }
        });
        payConfimBtn = findViewById(R.id.payConfimBtn);
        payDescTV = findViewById(R.id.payDescTV);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        payConfimBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payConfimBtn:
                showAll(v);
                break;
            default:
                break;
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_pay_token:
                TextView cancelTV = view.findViewById(R.id.cancelTV);
                cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                PwdEditText pwdET = view.findViewById(R.id.inputPwdEV);
                showSoftInputFromWindow(context,pwdET);
                break;
            default:
                break;
        }
    }

    //全屏弹出
    public void showAll(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View popView = LayoutInflater.from(this).inflate(R.layout.popup_pay_token, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(popView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_pay_token)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, popView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setOutsideTouchable(false)
                .setViewOnclickListener(this)
                .create();
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
