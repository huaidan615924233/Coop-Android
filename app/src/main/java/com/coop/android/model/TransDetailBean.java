package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class TransDetailBean {
    private String entrId;
    private String inveId;
    private String projectId;
    private String transHeight;
    private String transHash;
    private String transTimestamp;
    private String inveRealName;
    private String entrRealName;
    private String inveName;
    private String entrName;
    private int EntrBalanceAmount;
    private int InveBalanceAmount;
    private int tokenNum;
    private String projectName;
    private String transNo;
    private String payRemark;
    private String receiveRemark;
    private String createTime;
    private String stockUrl;
    private String authorizeUrl;
    private String id;
    private List<AgreementBean> file_list;

    public List<AgreementBean> getFile_list() {
        return file_list;
    }

    public void setFile_list(List<AgreementBean> file_list) {
        this.file_list = file_list;
    }

    public String getEntrId() {
        return entrId;
    }

    public void setEntrId(String entrId) {
        this.entrId = entrId;
    }

    public String getInveId() {
        return inveId;
    }

    public void setInveId(String inveId) {
        this.inveId = inveId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTransHeight() {
        return transHeight;
    }

    public void setTransHeight(String transHeight) {
        this.transHeight = transHeight;
    }

    public String getTransHash() {
        return transHash;
    }

    public void setTransHash(String transHash) {
        this.transHash = transHash;
    }

    public String getTransTimestamp() {
        return transTimestamp;
    }

    public void setTransTimestamp(String transTimestamp) {
        this.transTimestamp = transTimestamp;
    }

    public String getInveRealName() {
        return inveRealName;
    }

    public void setInveRealName(String inveRealName) {
        this.inveRealName = inveRealName;
    }

    public String getEntrRealName() {
        return entrRealName;
    }

    public void setEntrRealName(String entrRealName) {
        this.entrRealName = entrRealName;
    }

    public String getInveName() {
        return inveName;
    }

    public void setInveName(String inveName) {
        this.inveName = inveName;
    }

    public String getEntrName() {
        return entrName;
    }

    public void setEntrName(String entrName) {
        this.entrName = entrName;
    }

    public int getEntrBalanceAmount() {
        return EntrBalanceAmount;
    }

    public void setEntrBalanceAmount(int entrBalanceAmount) {
        EntrBalanceAmount = entrBalanceAmount;
    }

    public int getInveBalanceAmount() {
        return InveBalanceAmount;
    }

    public void setInveBalanceAmount(int inveBalanceAmount) {
        InveBalanceAmount = inveBalanceAmount;
    }

    public int getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(int tokenNum) {
        this.tokenNum = tokenNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public String getReceiveRemark() {
        return receiveRemark;
    }

    public void setReceiveRemark(String receiveRemark) {
        this.receiveRemark = receiveRemark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStockUrl() {
        return stockUrl;
    }

    public void setStockUrl(String stockUrl) {
        this.stockUrl = stockUrl;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TransDetailBean{" +
                "entrId='" + entrId + '\'' +
                ", inveId='" + inveId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", transHeight='" + transHeight + '\'' +
                ", transHash='" + transHash + '\'' +
                ", transTimestamp='" + transTimestamp + '\'' +
                ", inveRealName='" + inveRealName + '\'' +
                ", entrRealName='" + entrRealName + '\'' +
                ", inveName='" + inveName + '\'' +
                ", entrName='" + entrName + '\'' +
                ", EntrBalanceAmount=" + EntrBalanceAmount +
                ", InveBalanceAmount=" + InveBalanceAmount +
                ", tokenNum=" + tokenNum +
                ", projectName='" + projectName + '\'' +
                ", transNo='" + transNo + '\'' +
                ", payRemark='" + payRemark + '\'' +
                ", receiveRemark='" + receiveRemark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", stockUrl='" + stockUrl + '\'' +
                ", authorizeUrl='" + authorizeUrl + '\'' +
                ", id='" + id + '\'' +
                ", file_list=" + file_list +
                '}';
    }
}
