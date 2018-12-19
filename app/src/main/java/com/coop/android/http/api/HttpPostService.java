package com.coop.android.http.api;

import com.coop.android.model.LoginResponseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit_rx.Api.BaseResultEntity;
import rx.Observable;

/**
 * 测试接口service-post相关
 * Created by WZG on 2016/12/19.
 */

public interface HttpPostService {
    @FormUrlEncoded
    @POST(HttpPostApi.LOGIN_URL)
    Observable<BaseResultEntity<LoginResponseBean>> getLogin(@Field("phoneNumber") String phoneNumber, @Field("code") String code);

    @FormUrlEncoded
    @POST(HttpPostApi.GET_VERIFY)
    Observable<BaseResultEntity<String>> getVerify(@Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST(HttpPostApi.ADD_ROLE)
    Observable<BaseResultEntity<String>> getAddRole(@Field("nickName") String nickName, @Field("custId") String userId, @Field("nickName") String roleType);
}
