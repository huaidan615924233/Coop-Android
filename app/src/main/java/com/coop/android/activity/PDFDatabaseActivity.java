package com.coop.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.utils.MyRemotePDFViewPager;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class PDFDatabaseActivity extends CBaseActivity implements DownloadFile.Listener {
    private static final String TAG = "PDFDatabaseActivity";
    protected Toolbar toolBar;
    private RelativeLayout pdf_root;
    private ProgressBar pb_bar;
    private MyRemotePDFViewPager remotePDFViewPager;
    private String mUrl = "http://css4.pub/2015/textbook/somatosensory.pdf";
    private String fileName = "";
    private PDFPagerAdapter adapter;

    public static Intent createIntent(Context context, String fileName, String url) {
        return new Intent(context, PDFDatabaseActivity.class).putExtra("url", url).putExtra("fileName", fileName);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        intent = getIntent();
        mUrl = intent.getStringExtra("url");
        fileName = intent.getStringExtra("fileName");
        Log.e(TAG, mUrl);
        pdf_root = findViewById(R.id.remote_pdf_root);
        pb_bar = findViewById(R.id.pb_bar);
        tvBaseTitle.setText(fileName);
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
    }

    @Override
    public void initData() {
        setDownloadListener();
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

    /*设置监听*/
    protected void setDownloadListener() {
        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new MyRemotePDFViewPager(this, mUrl, listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);
    }

    /*加载成功调用*/
    @Override
    public void onSuccess(String url, String destinationPath) {
        pb_bar.setVisibility(View.GONE);
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
    }

    /*更新视图*/
    private void updateLayout() {
        pdf_root.removeAllViewsInLayout();
        pdf_root.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ToastUtil.showLongToast(mContext, "左右滑动查看");
    }

    /*加载失败调用*/
    @Override
    public void onFailure(Exception e) {
        pb_bar.setVisibility(View.GONE);
        Toast.makeText(this, "数据加载错误+" + e.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("PDFError", e.getMessage());
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("加载pdf页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("加载pdf页面");
        MobclickAgent.onPause(this);
    }
}