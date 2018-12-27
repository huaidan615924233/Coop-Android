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
    public TransBean() {
        super();
    }

    public TransBean(String projectName, String projectId, String entrCustId, String inveCustId, String entrRemark, String inveRemark, String tokenNum, String payPassword, String projectToken, String stockPercent, String projectTokenPrice) {
        this.projectName = projectName;
        this.projectId = projectId;
        this.entrCustId = entrCustId;
        this.inveCustId = inveCustId;
        this.entrRemark = entrRemark;
        this.inveRemark = inveRemark;
        this.tokenNum = tokenNum;
        this.payPassword = payPassword;
        this.projectToken = projectToken;
        this.stockPercent = stockPercent;
        this.projectTokenPrice = projectTokenPrice;
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
                '}';
    }
}
