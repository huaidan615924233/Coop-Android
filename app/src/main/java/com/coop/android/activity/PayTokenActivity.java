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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.TransBean;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.Md5EncryptionHelper;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CommonPopupWindow;
import com.coop.android.view.PwdEditText;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import retrofit_rx.http.HttpManager;
import retrofit_rx.listener.HttpOnNextListener;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class PayTokenActivity extends CBaseActivity implements View.OnClickListener, CommonPopupWindow.ViewInterface, AlertDialog.OnDialogButtonClickListener {
    private static final String TAG = "PayTokenActivity";
    private TransBean transBean;
    protected Toolbar toolBar;
    private Button payConfimBtn;
    private CommonPopupWindow popupWindow;

    private TextView payDescTV;
    private TextView pwdErrorTV, assetNameTV;
    private EditText descET, payTokenNumberET;

    private TagContainerLayout tagContainerLayout;
    private List<String> tagList;
    private int currentPosition = -1;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, TransBean transBean) {
        return new Intent(context, PayTokenActivity.class).putExtra("transBean", transBean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_token);
        intent = getIntent();
        transBean = intent.getParcelableExtra("transBean");
        StatusBarUtils.setStatusBarColorDefault(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_pay_token) + transBean.getProjectName());
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                if (!StringUtil.isEmpty(descET.getText().toString())) {
                    new AlertDialog(mContext, "提示", "您确定要放弃本次的数字权证转让吗?", true, 0, new AlertDialog.OnDialogButtonClickListener() {
                        @Override
                        public void onDialogButtonClick(int requestCode, boolean isPositive) {
                            if (isPositive)
                                finish();
                        }
                    }).show();
                    return;
                }
                finish();
            }
        });
        payConfimBtn = findViewById(R.id.payConfimBtn);
//        payDescTV = findViewById(R.id.payDescTV);
//        payDescTV.setText("数字权证剩余量" + transBean.getProjectToken() +
//                "（发行占比" + transBean.getStockPercent() + "，数字权证单价:" + transBean.getProjectTokenPrice() + "元）");
        descET = findViewById(R.id.descET);
        assetNameTV = findViewById(R.id.assetNameTV);
        payTokenNumberET = findViewById(R.id.payTokenNumberET);
        tagContainerLayout = findViewById(R.id.tagContainerLayout);
        assetNameTV.setText(transBean.getAsset_name());
    }

    @Override
    public void initData() {
        tagList = new ArrayList<String>();
        tagList.add("商业咨询");
        tagList.add("企业战略咨询");
        tagList.add("股权架构咨询");
        tagList.add("产品优化咨询");
        tagList.add("融资支持");
        tagList.add("销售支持");
        tagList.add("流量支持");
        tagContainerLayout.setTags(tagList);
    }

    @Override
    public void initEvent() {
        payConfimBtn.setOnClickListener(this);
        tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                tagContainerLayout.removeAllTags();
                for (int i = 0; i < tagList.size(); i++) {
                    if (i == position && currentPosition != position) {
                        descET.setText(text);
                        currentPosition = position;
                        tagContainerLayout.setTagTextColor(getResources().getColor(R.color.white));  //设置标签字的颜色
                        tagContainerLayout.setTagBackgroundColor(getResources().getColor(R.color.colorPrimary));  //设置单个item背景颜色
                    } else if (i == position && currentPosition == position) {
                        descET.setText("");
                        currentPosition = -1;
                        tagContainerLayout.setTagTextColor(getResources().getColor(R.color.color_B9C1CF));
                        tagContainerLayout.setTagBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        tagContainerLayout.setTagTextColor(getResources().getColor(R.color.color_B9C1CF));
                        tagContainerLayout.setTagBackgroundColor(getResources().getColor(R.color.white));
                    }
                    tagContainerLayout.addTag(tagList.get(i));
                }
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payConfimBtn:
                payToken(descET.getText().toString(), payTokenNumberET.getText().toString(), v);
                break;
            default:
                break;
        }
    }

    //获取验证码信息，判断是否有手机号码
    private void payToken(String desc, String transToken, View v) {
        try {
            Integer.parseInt(transToken);
        } catch (Exception e) {
            new AlertDialog(mContext, "提示", "输入的转让数量太大", true, 0, this).show();
            Log.e(TAG, e.getMessage());
            return;
        }
        if (StringUtil.isEmpty(desc)) {
            Log.e(TAG, "mobile=" + desc);
            new AlertDialog(mContext, "提示", "请输入备注信息", true, 0, this).show();
        } else if (StringUtil.isEmpty(transToken)) {
            new AlertDialog(mContext, "提示", "请输入转让数字权证数量", true, 0, this).show();
        } else {
            transBean.setEntrCustId(UserConfigs.getInstance().getId());
            transBean.setEntrRemark(desc);
            transBean.setTokenNum(transToken);
            showAll(v);
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_pay_token:
                TextView payTokenNumberTV = view.findViewById(R.id.payTokenNumberTV);
                payTokenNumberTV.setText("数量:" + payTokenNumberET.getText());
                TextView cancelTV = view.findViewById(R.id.cancelTV);
                cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                final PwdEditText pwdET = view.findViewById(R.id.inputPwdEV);
                showSoftInputFromWindow(context, pwdET);
                TextView forgetPwdTV = view.findViewById(R.id.forgetPwdTV);
                forgetPwdTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(UpdatePasswordActivity.createIntent(mContext, ConstantUtil.UPDATEPASSWORD));
                    }
                });
                pwdErrorTV = view.findViewById(R.id.pwdErrorTV);
                pwdErrorTV.setText("");
                TextView payTV = view.findViewById(R.id.payTV);
                payTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (StringUtil.isEmpty(pwdET.getText().toString()) || pwdET.getText().toString().length() < 6) {
                            pwdErrorTV.setText(getResources().getString(R.string.txt_password_failed));
                            return;
                        }
                        transBean.setPayPassword(Md5EncryptionHelper.getMD5WithSalt(pwdET.getText().toString(), UserConfigs.getInstance().getSalt()));

                        HttpPostApi postEntity = new HttpPostApi(payOnNextListener, context, HttpPostApi.COOP_TRANS, true);
                        postEntity.setTransBean(transBean);
                        HttpManager manager = HttpManager.getInstance();
                        manager.doHttpDeal(postEntity);
                    }
                });
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
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {

    }

    HttpOnNextListener payOnNextListener = new HttpOnNextListener<String>() {

        @Override
        public void onNext(String string, int code) {
            if (code == 700) {
                ToastUtil.showShortToast(getApplicationContext(), "登录失效，请重新登录!");
                startActivity(LoginActivity.createIntent(mContext, true));
                return;
            }
            if (code == 501) {
                ToastUtil.showShortToast(getApplicationContext(), "Token不足!");
                return;
            }
            if (code == 502) {
                pwdErrorTV.setText(getResources().getString(R.string.txt_password_failed));
                ToastUtil.showShortToast(getApplicationContext(), getResources().getString(R.string.txt_password_failed));
                return;
            }
            if (code == 503) {
                ToastUtil.showShortToast(getApplicationContext(), "不能转让给自己!");
                return;
            }
            if (code == 505) {
                ToastUtil.showShortToast(getApplicationContext(), "用户不在可交易名单范围内!");
                return;
            }
            String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date(System.currentTimeMillis()));
            context.startActivity(TransVoucherActivity.createIntent(context, "(wo)", transBean.getProjectName(),
                    transBean.getTokenNum(), date, UserConfigs.getInstance().getName(), transBean.getName()));
            //转让成功事件统计
            MobclickAgent.onEvent(mContext, "onClick", "转让成功");
//            ToastUtil.showShortToast(getApplicationContext(), "转让成功!");
            finish();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(getApplicationContext(), e.getMessage());
            Log.e(TAG, getResources().getString(R.string.txt_server_error) + e.getMessage());
        }
    };

    /**
     * 返回提示
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (!StringUtil.isEmpty(descET.getText().toString())) {
                new AlertDialog(mContext, "提示", "您确定要放弃本次的数字权证转让吗?", true, 0, new AlertDialog.OnDialogButtonClickListener() {
                    @Override
                    public void onDialogButtonClick(int requestCode, boolean isPositive) {
                        if (isPositive)
                            finish();
                    }
                }).show();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("支付Token页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("支付Token页面");
        MobclickAgent.onPause(this);
    }
}
