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
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class AddTransactionDescActivity extends CBaseActivity implements View.OnClickListener {
    protected Toolbar toolBar;
    private EditText descET;
    private Button nextBtn;
    private TagContainerLayout tagContainerLayout;
    private List<String> tagList;
    private int currentPosition = -1;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, AddTransactionDescActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtransaction);
        StatusBarUtils.setStatusBarColorDefault(this);
        initView();
        initEvent();
        initData();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_trans_desc));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtil.isEmpty(descET.getText().toString())) {
                    new AlertDialog(mContext, "提示", "您确定要放弃本次数字权证转让吗?", true, 0, new AlertDialog.OnDialogButtonClickListener() {
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
        descET = findViewById(R.id.descET);
        nextBtn = findViewById(R.id.nextBtn);
        tagContainerLayout = findViewById(R.id.tagContainerLayout);
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
        nextBtn.setOnClickListener(this);
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
            case R.id.nextBtn:
                if (TextUtils.isEmpty(descET.getText().toString())) {
                    ToastUtil.showShortToast(mContext, "请输入备注信息");
                    return;
                }
                startActivity(QrcodeActivity.createIntent(mContext, descET.getText().toString()));
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

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
                new AlertDialog(mContext, "提示", "您确定要放弃本次数字权证转让吗?", true, 0, new AlertDialog.OnDialogButtonClickListener() {
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
        MobclickAgent.onPageStart("二维码添加备注页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("二维码添加备注页面");
        MobclickAgent.onPause(this);
    }
}
