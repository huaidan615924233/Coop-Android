package com.coop.android.http.api;

import android.app.Activity;

import retrofit2.Retrofit;
import retrofit_rx.Api.BaseApi;
import retrofit_rx.listener.HttpOnNextListener;
import rx.Observable;

/**
 * 测试数据
 * Created by WZG on 2016/7/16.
 */
public class HttpPostApi extends BaseApi {
    public static final String LOGIN_URL = "coopApi/auth";
    public static final String GET_VERIFY = "coopApi/sendSms";
    public static final String ADD_ROLE = "coopApi/addRole";
    private String requestUrl;
    //    接口需要传入的参数 可自定义不同类型
    private String phoneNumber;
    private String code;
    private String userId;
    private String roleType;
    private String nickName;

    /**
     * 默认初始化需要给定回调和rx周期类
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     *
     * @param listener
     */
    public HttpPostApi(HttpOnNextListener listener, Activity activity, String url, boolean isShowProgress) {
        super(listener, activity);
        setShowProgress(isShowProgress);
        setCancel(true);
        setBaseUrl("http://39.96.36.106:8080/");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24 * 60 * 60);
        requestUrl = url;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService service = retrofit.create(HttpPostService.class);
        if (requestUrl.equals(LOGIN_URL)) {
            return service.getLogin(getPhoneNumber(), getCode());
        } else if (requestUrl.equals(GET_VERIFY)) {
            return service.getVerify(getPhoneNumber());
        } else if (requestUrl.equals(ADD_ROLE)) {
            return service.getAddRole(getNickName(), getUserId(), getRoleType());
        } else
            return service.getLogin(getPhoneNumber(), getCode());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
