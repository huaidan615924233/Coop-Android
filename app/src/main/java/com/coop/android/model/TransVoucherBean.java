package com.coop.android.model;

/**
 * Created by MR-Z on 2019/3/7.
 */
public class TransVoucherBean {
    private String voucherName, transNum, transDate, entrRealName, inveRealName, tokenName;

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getEntrRealName() {
        return entrRealName;
    }

    public void setEntrRealName(String entrRealName) {
        this.entrRealName = entrRealName;
    }

    public String getInveRealName() {
        return inveRealName;
    }

    public void setInveRealName(String inveRealName) {
        this.inveRealName = inveRealName;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    @Override
    public String toString() {
        return "TransVoucherBean{" +
                "voucherName='" + voucherName + '\'' +
                ", transNum='" + transNum + '\'' +
                ", transDate='" + transDate + '\'' +
                ", entrRealName='" + entrRealName + '\'' +
                ", inveRealName='" + inveRealName + '\'' +
                ", tokenName='" + tokenName + '\'' +
                '}';
    }
}
