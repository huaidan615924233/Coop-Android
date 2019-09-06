package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2018/12/18.
 */
public class CoopPartnerListResponseBean {
    private List<PartnerTransBean> trans;

    public List<PartnerTransBean> getTrans() {
        return trans;
    }

    public void setTrans(List<PartnerTransBean> trans) {
        this.trans = trans;
    }

    @Override
    public String toString() {
        return "CoopPartnerListResponseBean{" +
                "trans=" + trans +
                '}';
    }
}
