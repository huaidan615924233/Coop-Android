package com.coop.android.webview;

/**
 * JS与原生的交互方法
 * Create by Mr.Z on 2018-8-9
 */
public interface JsNativeCallBack {

    /**
     * 页面交互 ---token失效回调
     */
    void tokenOutTimeEvent();

    /**
     * 查询结果
     *
     * @param result
     */
    void checkResult(String result);
}