package com.coop.android.model;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class TransInfoBean {
    private String id;
    private String entrName;
    private String createTime;
    private String inveName;
    private String tokenNum;
    private String entrId;
    private String balanceAmount;
    private String inveId;
    private String projectId;
    private String inveAvatar;
    private String transNo;
    private String payRemark;
    private String receiveRemark;
    private String projectName;
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntrName() {
        return entrName;
    }

    public void setEntrName(String entrName) {
        this.entrName = entrName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInveName() {
        return inveName;
    }

    public void setInveName(String inveName) {
        this.inveName = inveName;
    }

    public String getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(String tokenNum) {
        this.tokenNum = tokenNum;
    }

    public String getEntrId() {
        return entrId;
    }

    public void setEntrId(String entrId) {
        this.entrId = entrId;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
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

    public String getInveAvatar() {
        return inveAvatar;
    }

    public void setInveAvatar(String inveAvatar) {
        this.inveAvatar = inveAvatar;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "TransInfoBean{" +
                "id='" + id + '\'' +
                ", entrName='" + entrName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", inveName='" + inveName + '\'' +
                ", tokenNum='" + tokenNum + '\'' +
                ", entrId='" + entrId + '\'' +
                ", balanceAmount='" + balanceAmount + '\'' +
                ", inveId='" + inveId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", inveAvatar='" + inveAvatar + '\'' +
                ", transNo='" + transNo + '\'' +
                ", payRemark='" + payRemark + '\'' +
                ", receiveRemark='" + receiveRemark + '\'' +
                ", projectName='" + projectName + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
