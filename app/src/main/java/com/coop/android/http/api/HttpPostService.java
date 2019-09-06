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

import com.coop.android.model.AppCommonBean;
import com.coop.android.model.CoopEnterListResponseBean;
import com.coop.android.model.CoopPartnerListResponseBean;
import com.coop.android.model.CoopPartnerResponseBean;
import com.coop.android.model.CoopResponseBean;
import com.coop.android.model.EnterTransListBean;
import com.coop.android.model.LoginResponseBean;
import com.coop.android.model.ProjectDetailResponseBean;
import com.coop.android.model.ProjectTeamListResponseBean;
import com.coop.android.model.TransDetailBean;
import com.coop.android.model.WhiteListResponseBean;

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
    @POST(HttpPostApi.APP_VERSION)
    Observable<BaseResultEntity<AppCommonBean>> getAppVersion(@Field("app_type") String appType, @Field("current_version") String currentVersion);

    @FormUrlEncoded
    @POST(HttpPostApi.GET_VERIFY)
    Observable<BaseResultEntity<String>> getVerify(@Field("phoneNumber") String phoneNumber, @Field("type") int type);

    @FormUrlEncoded
    @POST(HttpPostApi.CHANGE_ROLE)
    Observable<BaseResultEntity<String>> getChangeRole(@Field("custId") String userId, @Field("roleType") String roleType);

    @FormUrlEncoded
    @POST(HttpPostApi.COOP_PROJECT_LIST)
    Observable<BaseResultEntity<CoopResponseBean>> getCoopProjectList(@Field("custId") String userId);

    @FormUrlEncoded
    @POST(HttpPostApi.COOP_ENTER_LIST)
    Observable<BaseResultEntity<CoopEnterListResponseBean>> getCoopEnterList(@Field("cust_id") String userId);

    @FormUrlEncoded
    @POST(HttpPostApi.COOP_INVE_LIST)
    Observable<BaseResultEntity<CoopPartnerListResponseBean>> getCoopInveList(@Field("cust_id") String userId);

    @FormUrlEncoded
    @POST(HttpPostApi.COOP_RECEIVABLE_LIST)
    Observable<BaseResultEntity<CoopPartnerResponseBean>> getCoopPartnerList(@Field("custId") String userId);

    @FormUrlEncoded
    @POST(HttpPostApi.PROJECT_DETAIL)
    Observable<BaseResultEntity<ProjectDetailResponseBean>> getProjectDetail(@Field("asset_id") String assetId);

    @FormUrlEncoded
    @POST(HttpPostApi.SET_PAY_PASSWORD)
    Observable<BaseResultEntity<String>> getSetPayPassword(@Field("phoneNumber") String phoneNumber, @Field("payPassword") String payPassword,
                                                           @Field("salt") String salt, @Field("code") String code);

    @FormUrlEncoded
    @POST(HttpPostApi.COOP_TRANS)
    Observable<BaseResultEntity<String>> getCoopTrans(@Field("entr_cust_id") String entrCustId, @Field("inve_cust_id") String inveCustId,
                                                      @Field("asset_id") String projectId, @Field("entr_remark") String entrRemark,
                                                      @Field("inve_remark") String inveRemark, @Field("token_num") int tokenNum,
                                                      @Field("pay_password") String payPassword, @Field("registration_id") String registrationId);

    @FormUrlEncoded
    @POST(HttpPostApi.AUTHENTICATION)
    Observable<BaseResultEntity<String>> getAuthEntication(@Field("custId") String userId, @Field("name") String name,
                                                           @Field("cardNo") String cardNo, @Field("nickName") String nickName, @Field("roleType") String roleType);

    @FormUrlEncoded
    @POST(HttpPostApi.TRANS_DETAIL)
    Observable<BaseResultEntity<TransDetailBean>> getTransDetail(@Field("trans_no") String transNo);

    @FormUrlEncoded
    @POST(HttpPostApi.MOBILE_RESET)
    Observable<BaseResultEntity<String>> getMobileReset(@Field("mobile_old") String mobileOld, @Field("mobile_new") String mobileNew,
                                                        @Field("code") String code);

    @FormUrlEncoded
    @POST(HttpPostApi.ENTER_TRANS_LIST)
    Observable<BaseResultEntity<EnterTransListBean>> getEnterTransList(@Field("asset_id") String assetId);

    @FormUrlEncoded
    @POST(HttpPostApi.PROJECT_TEAM_LIST)
    Observable<BaseResultEntity<ProjectTeamListResponseBean>> getTeamList(@Field("asset_id") String assetId);

    @FormUrlEncoded
    @POST(HttpPostApi.ASSET_WHITE_LIST)
    Observable<BaseResultEntity<WhiteListResponseBean>> getAssetWhiteList(@Field("mobile_no") String mobileNo, @Field("asset_id") String assetId);

    @FormUrlEncoded
    @POST(HttpPostApi.ADD_ASSET_WHITE)
    Observable<BaseResultEntity<String>> getAddAssetWhite(@Field("inve_role_id") String inveRoleId, @Field("asset_id") String assetId);
}
