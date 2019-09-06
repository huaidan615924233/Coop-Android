package com.coop.android.model;

/**
 * Created by MR-Z on 2018/12/18.
 */
public class UserInfo {
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
    private String lastLoginRole;   //1为创业者，2为协作方
    private String createTime;
    private String email;
    private String custNo;
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
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
                ", custNo='" + custNo + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
