package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2019/3/6.
 */
public class AssetBean {
    public static final int ASSET_TYPE_1 = 1;
    public static final int ASSET_TYPE_2 = 2;
    public static final int ASSET_TYPE_3 = 3;
    public static final int ASSET_TYPE_4 = 4;
    public static final String ASSET_TYPE_NAME_1 = "个人资产";
    public static final String ASSET_TYPE_NAME_2 = "资源拓展资产";
    public static final String ASSET_TYPE_NAME_3 = "内部激励资产";
    public static final String ASSET_TYPE_NAME_4 = "合伙管理资产";
    private String stock_percent;
    private String project_amount;
    private String token_name;
    private String project_token;
    private String asset_name;
    private int asset_type;
    private String id;

    public int getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(int asset_type) {
        this.asset_type = asset_type;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    private List<AgreementBean> file_list;

    public List<AgreementBean> getFile_list() {
        return file_list;
    }

    public void setFile_list(List<AgreementBean> file_list) {
        this.file_list = file_list;
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
        return "AssetBean{" +
                "stock_percent='" + stock_percent + '\'' +
                ", project_amount='" + project_amount + '\'' +
                ", token_name='" + token_name + '\'' +
                ", project_token='" + project_token + '\'' +
                ", asset_name='" + asset_name + '\'' +
                ", asset_type='" + asset_type + '\'' +
                ", id='" + id + '\'' +
                ", file_list=" + file_list +
                '}';
    }
}
