package com.coop.android.webview;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import zuo.biao.library.util.Log;

public class WebViewJavascriptBridge {
    public static final String BRIDGE_NAME = "WebNativeBridge";
    public static final String JsBridgeName = "commentFun";
    private static final String TAG = "WebViewJavascriptBridge";

    private JsNativeCallBack mCallBack;

    private WebViewJavascriptBridge() {
    }

    public static WebViewJavascriptBridge getInstance() {
        return new WebViewJavascriptBridge();
    }

    public void regist(JsNativeCallBack callback) {
        this.mCallBack = callback;
    }

    public void unregist() {
        this.mCallBack = null;
    }

    /**
     * token失效
     */
    @JavascriptInterface
    public void nativeFun_tokenOutTimeEvent(String json) {
        Log.w(TAG, "nativeFun_tokenOutTimeEvent:" + json);
        mCallBack.tokenOutTimeEvent();
    }

    /**
     * 查询结果
     *
     * @param json url       {String}  查询结果url
     */
    @JavascriptInterface
    public void nativeFun_checkResult(String json) {
        Log.w(TAG, "nativeFun_checkResult json: " + json);
        JSONObject object = JSON.parseObject(json);
//        String url = object.get("url").toString();
//        String type = object.get("type").toString();
        mCallBack.checkResult(json);
    }
}