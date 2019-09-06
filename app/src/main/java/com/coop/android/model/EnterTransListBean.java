package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2018/12/18.
 */
public class EnterTransListBean {
    private List<EnterTransBean> trans_list;
    private String stock_percent;
    private String project_amount;
    private String token_name;
    private String project_token;
    private String id;

    public List<EnterTransBean> getTrans_list() {
        return trans_list;
    }

    public void setTrans_list(List<EnterTransBean> trans_list) {
        this.trans_list = trans_list;
    }

    public String getStock_percent() {
        return stock_percent;
    }

    public void setStock_percent(String stock_percent) {
        this.stock_percent = stock_percent;
    }

    public String getProject_amount() {
        return project_amount;
    }

    public void setProject_amount(String project_amount) {
        this.project_amount = project_amount;
    }

    public String getToken_name() {
        return token_name;
    }

    public void setToken_name(String token_name) {
        this.token_name = token_name;
    }

    public String getProject_token() {
        return project_token;
    }

    public void setProject_token(String project_token) {
        this.project_token = project_token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EnterTransListBean{" +
                "trans_list=" + trans_list +
                ", stock_percent='" + stock_percent + '\'' +
                ", project_amount='" + project_amount + '\'' +
                ", token_name='" + token_name + '\'' +
                ", project_token='" + project_token + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
