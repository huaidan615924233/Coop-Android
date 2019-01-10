package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class ProjectBean {
    private List<TransInfoBean> list;
    private String id;
    private String name;
    private String address;
    private String authorizeUrl;
    private String companyName;
    private String createTime;
    private String entrId;
    private String foundTime;
    private String isDisabled;
    private String logo;
    private String projectAmount;
    private String projectPercent;
    private String projectToken;
    private String remark;
    private String serviceAmount;
    private String state;
    private String status;
    private String stockAmount;
    private String stockNum;
    private String stockPercent;
    private String stockUrl;
    private String tokenName;
    private String type;
    private String custRoleId;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<TransInfoBean> getList() {
        return list;
    }

    public void setList(List<TransInfoBean> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEntrId() {
        return entrId;
    }

    public void setEntrId(String entrId) {
        this.entrId = entrId;
    }

    public String getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(String foundTime) {
        this.foundTime = foundTime;
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(String projectAmount) {
        this.projectAmount = projectAmount;
    }

    public String getProjectPercent() {
        return projectPercent;
    }

    public void setProjectPercent(String projectPercent) {
        this.projectPercent = projectPercent;
    }

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(String stockAmount) {
        this.stockAmount = stockAmount;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockPercent() {
        return stockPercent;
    }

    public void setStockPercent(String stockPercent) {
        this.stockPercent = stockPercent;
    }

    public String getStockUrl() {
        return stockUrl;
    }

    public void setStockUrl(String stockUrl) {
        this.stockUrl = stockUrl;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustRoleId() {
        return custRoleId;
    }

    public void setCustRoleId(String custRoleId) {
        this.custRoleId = custRoleId;
    }

    @Override
    public String toString() {
        return "ProjectBean{" +
                "list=" + list +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", authorizeUrl='" + authorizeUrl + '\'' +
                ", companyName='" + companyName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", entrId='" + entrId + '\'' +
                ", foundTime='" + foundTime + '\'' +
                ", isDisabled='" + isDisabled + '\'' +
                ", logo='" + logo + '\'' +
                ", projectAmount='" + projectAmount + '\'' +
                ", projectPercent='" + projectPercent + '\'' +
                ", projectToken='" + projectToken + '\'' +
                ", remark='" + remark + '\'' +
                ", serviceAmount='" + serviceAmount + '\'' +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", stockAmount='" + stockAmount + '\'' +
                ", stockNum='" + stockNum + '\'' +
                ", stockPercent='" + stockPercent + '\'' +
                ", stockUrl='" + stockUrl + '\'' +
                ", tokenName='" + tokenName + '\'' +
                ", type='" + type + '\'' +
                ", custRoleId='" + custRoleId + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
