package com.coop.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MR-Z on 2018/12/25.
 */
public class TransBean implements Parcelable {
    private String projectName;
    private String projectId;
    private String entrCustId;
    private String inveCustId;
    private String entrRemark;
    private String inveRemark;
    private String tokenNum;
    private String payPassword;
    private String projectToken;
    private String stockPercent;
    private String projectTokenPrice;
    private String name;
    private String asset_name;
    private String registrationId;

    public TransBean() {
    }

    protected TransBean(Parcel in) {
        projectName = in.readString();
        projectId = in.readString();
        entrCustId = in.readString();
        inveCustId = in.readString();
        entrRemark = in.readString();
        inveRemark = in.readString();
        tokenNum = in.readString();
        payPassword = in.readString();
        projectToken = in.readString();
        stockPercent = in.readString();
        projectTokenPrice = in.readString();
        name = in.readString();
        asset_name = in.readString();
        registrationId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(projectName);
        dest.writeString(projectId);
        dest.writeString(entrCustId);
        dest.writeString(inveCustId);
        dest.writeString(entrRemark);
        dest.writeString(inveRemark);
        dest.writeString(tokenNum);
        dest.writeString(payPassword);
        dest.writeString(projectToken);
        dest.writeString(stockPercent);
        dest.writeString(projectTokenPrice);
        dest.writeString(name);
        dest.writeString(asset_name);
        dest.writeString(registrationId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransBean> CREATOR = new Creator<TransBean>() {
        @Override
        public TransBean createFromParcel(Parcel in) {
            return new TransBean(in);
        }

        @Override
        public TransBean[] newArray(int size) {
            return new TransBean[size];
        }
    };

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEntrCustId() {
        return entrCustId;
    }

    public void setEntrCustId(String entrCustId) {
        this.entrCustId = entrCustId;
    }

    public String getInveCustId() {
        return inveCustId;
    }

    public void setInveCustId(String inveCustId) {
        this.inveCustId = inveCustId;
    }

    public String getEntrRemark() {
        return entrRemark;
    }

    public void setEntrRemark(String entrRemark) {
        this.entrRemark = entrRemark;
    }

    public String getInveRemark() {
        return inveRemark;
    }

    public void setInveRemark(String inveRemark) {
        this.inveRemark = inveRemark;
    }

    public String getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(String tokenNum) {
        this.tokenNum = tokenNum;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    public String getStockPercent() {
        return stockPercent;
    }

    public void setStockPercent(String stockPercent) {
        this.stockPercent = stockPercent;
    }

    public String getProjectTokenPrice() {
        return projectTokenPrice;
    }

    public void setProjectTokenPrice(String projectTokenPrice) {
        this.projectTokenPrice = projectTokenPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String toString() {
        return "TransBean{" +
                "projectName='" + projectName + '\'' +
                ", projectId='" + projectId + '\'' +
                ", entrCustId='" + entrCustId + '\'' +
                ", inveCustId='" + inveCustId + '\'' +
                ", entrRemark='" + entrRemark + '\'' +
                ", inveRemark='" + inveRemark + '\'' +
                ", tokenNum='" + tokenNum + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", projectToken='" + projectToken + '\'' +
                ", stockPercent='" + stockPercent + '\'' +
                ", projectTokenPrice='" + projectTokenPrice + '\'' +
                ", name='" + name + '\'' +
                ", asset_name='" + asset_name + '\'' +
                ", registrationId='" + registrationId + '\'' +
                '}';
    }
}
