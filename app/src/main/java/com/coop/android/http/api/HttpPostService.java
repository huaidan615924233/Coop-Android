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

import com.coop.android.model.CoopPartnerResponseBean;
import com.coop.android.model.CoopResponseBean;
import com.coop.android.model.LoginResponseBean;
import com.coop.android.model.ProjectDetailResponseBean;
import com.coop.android.model.TransDetailBean;

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
                                                      @Field("payPassword") String payPassword,@Field("registrationId") String registrationId);

    @FormUrlEncoded
    @POST(HttpPostApi.AUTHENTICATION)
    Observable<BaseResultEntity<String>> getAuthEntication(@Field("custId") String userId, @Field("name") String name,
                                                           @Field("cardNo") String cardNo);

    @FormUrlEncoded
    @POST(HttpPostApi.TRANS_DETAIL)
    Observable<BaseResultEntity<TransDetailBean>> getTransDetail(@Field("account") String account, @Field("transNo") String transNo,
                                                                 @Field("roleType") String roleType);
}
