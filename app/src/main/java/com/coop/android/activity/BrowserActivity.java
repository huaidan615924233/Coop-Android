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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.utils.DensityUtil;
import com.coop.android.view.ProgressView;
import com.coop.android.webview.JsNativeCallBack;
import com.coop.android.webview.WebViewJavascriptBridge;
import com.umeng.analytics.MobclickAgent;

import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class BrowserActivity extends CBaseActivity implements JsNativeCallBack {
    private static final String TAG = "BrowserActivity";
    protected Toolbar toolBar;
    //    private String url = "http://www.baidu.com";
    private String url = "http://www.51coop.cn/block-chain-2.html";
    private LinearLayout mRootWebViewLayout;
    private WebView mBrowserWebView;
    private ProgressView progressView;//进度条
    private WebViewJavascriptBridge mJavaBridge;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, BrowserActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getString(R.string.txt_title_browser));
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
    }

    @Override
    public void initData() {
        initWebView(url);
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

    private void initWebView(String url) {
        // 初始化JSWebView调试
        mJavaBridge = WebViewJavascriptBridge.getInstance();
        mJavaBridge.regist(this);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        mRootWebViewLayout = findViewById(R.id.line_browser);
        mRootWebViewLayout.removeAllViews();
        //初始化进度条
        progressView = new ProgressView(this);
        progressView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(mContext, 2)));
        progressView.setColor(getResources().getColor(R.color.colorPrimary));
        progressView.setProgress(10);
        //把进度条加到Webview中
        mRootWebViewLayout.addView(progressView);

        // 将 WebView 添加到布局中
        mBrowserWebView = new WebView(this);
        mBrowserWebView.setLayoutParams(layoutParams);
        WebSettings settings = mBrowserWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setUserAgentString(settings.getUserAgentString() + " singsound(" + MobileUtil.getPackageName() + ")/" + MobileUtil.getAppVersionName());
        settings.setTextZoom(100);
        mBrowserWebView.addJavascriptInterface(mJavaBridge, WebViewJavascriptBridge.BRIDGE_NAME);
        mBrowserWebView.loadUrl(url);
        mBrowserWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                } else {
                    //更新进度
                    progressView.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mBrowserWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e(TAG, "onPageStarted");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.e(TAG, url);
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e(TAG, "onPageFinished");
                mBrowserWebView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载完毕进度条消失
                        progressView.setVisibility(View.GONE);
                        String loadUrl = "javascript:setLocation(" +
                                "\"Bearer " + UserConfigs.getInstance().getToken() + "," + UserConfigs.getInstance().getMobilePhone() + "\"" + ")";
                        Log.e(TAG, loadUrl);
                        mBrowserWebView.loadUrl(loadUrl);
                    }
                }, 1000);
                super.onPageFinished(view, url);
            }
        });
        mRootWebViewLayout.addView(mBrowserWebView);
    }

    @Override
    public void tokenOutTimeEvent() {

    }

    @Override
    public void checkResult(String result) {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("区块链查询页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("区块链查询页面");
        MobclickAgent.onPause(this);
    }
}
