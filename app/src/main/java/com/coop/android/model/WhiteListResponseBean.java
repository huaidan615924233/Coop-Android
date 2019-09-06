package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2019/3/6.
 */
public class WhiteListResponseBean {
    private List<WhiteMumberBean> whitelist;
    private String whitelist_max;
    private String whitelist_use;

    public List<WhiteMumberBean> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<WhiteMumberBean> whitelist) {
        this.whitelist = whitelist;
    }

    public String getWhitelist_max() {
        return whitelist_max;
    }

    public void setWhitelist_max(String whitelist_max) {
        this.whitelist_max = whitelist_max;
    }

    public String getWhitelist_use() {
        return whitelist_use;
    }

    public void setWhitelist_use(String whitelist_use) {
        this.whitelist_use = whitelist_use;
    }

    @Override
    public String toString() {
        return "WhiteListResponseBean{" +
                "whitelist=" + whitelist +
                ", whitelist_max='" + whitelist_max + '\'' +
                ", whitelist_use='" + whitelist_use + '\'' +
                '}';
    }
}
