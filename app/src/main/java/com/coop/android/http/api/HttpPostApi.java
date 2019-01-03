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
package com.coop.android.http.api;

import android.app.Activity;

import com.coop.android.UserConfigs;
import com.coop.android.model.TransBean;

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
    public static final String CHANGE_ROLE = "coopApi/changeRole";
    public static final String COOP_PROJECT_LIST = "coopApi/project/list";
    public static final String COOP_RECEIVABLE_LIST = "coopApi/receivable/list";
    public static final String PROJECT_DETAIL = "coopApi/project/detail";
    public static final String SET_PAY_PASSWORD = "coopApi/password/pay/update";
    public static final String COOP_TRANS = "coopApi/account/trans";
    private String requestUrl;
    //    接口需要传入的参数 可自定义不同类型
    private String phoneNumber;
    private String code;
    private String userId;
    private String roleType;
    private String nickName;
    private int type;
    private String projectId;
    private String payPassword;
    private String salt;
    private TransBean transBean;

    /**
     * 默认初始化需要给定回调和rx周期类
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     *
     * @param listener
     */
    public HttpPostApi(HttpOnNextListener listener, Activity activity, String url, boolean isShowProgress) {
        super(listener, activity, UserConfigs.getInstance().getToken());
        setShowProgress(isShowProgress);
        setCancel(true);
        setBaseUrl(com.coop.android.AppConfigs.APP_BASE_URL);
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
            return service.getVerify(getPhoneNumber(), getType());
        } else if (requestUrl.equals(ADD_ROLE)) {
            return service.getAddRole(getNickName(), getUserId(), getRoleType());
        } else if (requestUrl.equals(CHANGE_ROLE)) {
            return service.getChangeRole(getUserId(), getRoleType());
        } else if (requestUrl.equals(COOP_PROJECT_LIST)) {
            return service.getCoopProjectList(getUserId());
        } else if (requestUrl.equals(COOP_RECEIVABLE_LIST)) {
            return service.getCoopPartnerList(getUserId());
        } else if (requestUrl.equals(PROJECT_DETAIL)) {
            return service.getProjectDetail(getProjectId());
        } else if (requestUrl.equals(SET_PAY_PASSWORD)) {
            return service.getSetPayPassword(getPhoneNumber(), getPayPassword(), getSalt(), getCode());
        } else if (requestUrl.equals(COOP_TRANS)) {
            return service.getCoopTrans(transBean.getEntrCustId(), transBean.getInveCustId(), transBean.getProjectId(),
                    transBean.getEntrRemark(), transBean.getInveRemark(), Integer.parseInt(transBean.getTokenNum()), transBean.getPayPassword());
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public TransBean getTransBean() {
        return transBean;
    }

    public void setTransBean(TransBean transBean) {
        this.transBean = transBean;
    }
}
