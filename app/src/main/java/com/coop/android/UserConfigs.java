package com.coop.android;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 用户信息配置文件
 */
public class UserConfigs {
    private static UserConfigs userConfigs;

    private UserConfigs() {
    }

    public static UserConfigs getInstance() {
        if (userConfigs == null) {
            synchronized (UserConfigs.class) {
                if (userConfigs == null) {
                    userConfigs = new UserConfigs();
                }
            }
        }
        return userConfigs;
    }

    // 读取数据
    public static void loadUserInfo(String userInfo) {
        if (!TextUtils.isEmpty(userInfo)) {
            try {
                getInstance();
                JSONObject object = JSON.parseObject(userInfo);
                userConfigs.setToken(object.getString("token"));
                JSONObject jsonObject = object.getJSONObject("user");
                userConfigs.setId(jsonObject.getString("id"));
                userConfigs.setAccount(jsonObject.getString("account"));
                userConfigs.setAvatar(jsonObject.getString("avatar"));
                userConfigs.setCardNo(jsonObject.getString("cardNo"));
                userConfigs.setMobilePhone(jsonObject.getString("mobilePhone"));
                userConfigs.setName(jsonObject.getString("name"));
                userConfigs.setNickName(jsonObject.getString("nickName"));
                userConfigs.setRemark(jsonObject.getString("remark"));
                userConfigs.setSex(jsonObject.getString("sex"));
                userConfigs.setStatus(jsonObject.getString("status"));
                userConfigs.setWechatId(jsonObject.getString("wechatId"));
                userConfigs.setCardType(jsonObject.getString("cardType"));
                userConfigs.setLastLoginRole(jsonObject.getString("lastLoginRole"));
                userConfigs.setCreateTime(jsonObject.getString("createTime"));
                userConfigs.setEmail(jsonObject.getString("email"));
                userConfigs.setCustNo(jsonObject.getString("custNo"));
                userConfigs.setSalt(jsonObject.getString("salt"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String id;
    private String account;
    private String avatar;
    private String cardNo;
    private String mobilePhone;
    private String name;
    private String nickName;
    private String remark;
    private String sex;
    private String status;
    private String wechatId;
    private String cardType;
    private String lastLoginRole;
    private String createTime;
    private String email;
    private String token;
    private String custNo;
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLastLoginRole() {
        return lastLoginRole;
    }

    public void setLastLoginRole(String lastLoginRole) {
        this.lastLoginRole = lastLoginRole;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserConfigs{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", avatar='" + avatar + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", remark='" + remark + '\'' +
                ", sex='" + sex + '\'' +
                ", status='" + status + '\'' +
                ", wechatId='" + wechatId + '\'' +
                ", cardType='" + cardType + '\'' +
                ", lastLoginRole='" + lastLoginRole + '\'' +
                ", createTime='" + createTime + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", custNo='" + custNo + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
