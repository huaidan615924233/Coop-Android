package com.coop.android.http.api;

import com.coop.android.model.CoopPartnerResponseBean;
import com.coop.android.model.CoopResponseBean;
import com.coop.android.model.LoginResponseBean;
import com.coop.android.model.ProjectDetailResponseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit_rx.Api.BaseResultEntity;
import rx.Observable;

/**
 * 接口service-post相关
 */

public interface HttpPostService {
    @FormUrlEncoded
    @POST(HttpPostApi.LOGIN_URL)
    Observable<BaseResultEntity<LoginResponseBean>> getLogin(@Field("phoneNumber") String phoneNumber, @Field("code") String code);

    @FormUrlEncoded
    @POST(HttpPostApi.GET_VERIFY)
    Observable<BaseResultEntity<String>> getVerify(@Field("phoneNumber") String phoneNumber, @Field("type") int type);

    @FormUrlEncoded
    @POST(HttpPostApi.ADD_ROLE)
    Observable<BaseResultEntity<String>> getAddRole(@Field("nickName") String nickName, @Field("custId") String userId, @Field("roleType") String roleType);

    @FormUrlEncoded
    @POST(HttpPostApi.CHANGE_ROLE)
    Observable<BaseResultEntity<String>> getChangeRole(@Field("custId") String userId, @Field("roleType") String roleType);

    @FormUrlEncoded
    @POST(HttpPostApi.COOP_PROJECT_LIST)
    Observable<BaseResultEntity<CoopResponseBean>> getCoopProjectList(@Field("custId") String userId);

    @FormUrlEncoded
    @POST(HttpPostApi.COOP_RECEIVABLE_LIST)
    Observable<BaseResultEntity<CoopPartnerResponseBean>> getCoopPartnerList(@Field("custId") String userId);

    @FormUrlEncoded
    @POST(HttpPostApi.PROJECT_DETAIL)
    Observable<BaseResultEntity<ProjectDetailResponseBean>> getProjectDetail(@Field("projectId") String projectId);

    @FormUrlEncoded
    @POST(HttpPostApi.SET_PAY_PASSWORD)
    Observable<BaseResultEntity<String>> getSetPayPassword(@Field("phoneNumber") String phoneNumber, @Field("payPassword") String payPassword,
                                                           @Field("salt") String salt, @Field("code") String code);

    @FormUrlEncoded
    @POST(HttpPostApi.COOP_TRANS)
    Observable<BaseResultEntity<String>> getCoopTrans(@Field("entrCustId") String entrCustId, @Field("inveCustId") String inveCustId,
                                                      @Field("projectId") String projectId, @Field("entrRemark") String entrRemark,
                                                      @Field("inveRemark") String inveRemark, @Field("tokenNum") int tokenNum,
                                                      @Field("payPassword") String payPassword);
}
