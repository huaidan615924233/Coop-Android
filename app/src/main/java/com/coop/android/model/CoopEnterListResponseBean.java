package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2018/12/18.
 */
public class CoopEnterListResponseBean {
    private List<AssetBean> asset_list;
    private String found_time;
    private String project_no;
    private String create_time;
    private String company_name;
    private String name;
    private String logo;
    private String id;
    private String type;
    private String content;
    private String company_address;

    public List<AssetBean> getAsset_list() {
        return asset_list;
    }

    public void setAsset_list(List<AssetBean> asset_list) {
        this.asset_list = asset_list;
    }

    public String getFound_time() {
        return found_time;
    }

    public void setFound_time(String found_time) {
        this.found_time = found_time;
    }

    public String getProject_no() {
        return project_no;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    @Override
    public String toString() {
        return "CoopEnterListResponseBean{" +
                "asset_list=" + asset_list +
                ", found_time='" + found_time + '\'' +
                ", project_no='" + project_no + '\'' +
                ", create_time='" + create_time + '\'' +
                ", company_name='" + company_name + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", company_address='" + company_address + '\'' +
                '}';
    }
}
