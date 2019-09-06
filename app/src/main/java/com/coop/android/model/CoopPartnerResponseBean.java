package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2018/12/18.
 */
public class CoopPartnerResponseBean {
    private List<TransInfoBean> trans;

    public List<TransInfoBean> getTrans() {
        return trans;
    }

    public void setTrans(List<TransInfoBean> trans) {
        this.trans = trans;
    }

    @Override
    public String toString() {
        return "CoopPartnerResponseBean{" +
                "trans=" + trans +
                '}';
    }
}
