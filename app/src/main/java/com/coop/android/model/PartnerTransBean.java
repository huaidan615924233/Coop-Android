package com.coop.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MR-Z on 2018/12/25.
 */
public class PartnerTransBean implements Parcelable {

    private String trans_no;
    private String create_time;
    private String entr_avatar;
    private String inve_real_name;
    private String inve_balance_amount;
    private String asset_id;
    private String entr_remak;
    private String inve_nick_name;
    private String entr_id;
    private String token_num;
    private String inve_remark;
    private String inve_id;
    private String entr_nick_name;
    private String id;
    private String entr_balance_amount;
    private String entr_real_name;
    private String asset_name;
    private int asset_type;
    private String project_logo;

    public PartnerTransBean() {
    }

    protected PartnerTransBean(Parcel in) {
        trans_no = in.readString();
        create_time = in.readString();
        entr_avatar = in.readString();
        inve_real_name = in.readString();
        inve_balance_amount = in.readString();
        asset_id = in.readString();
        entr_remak = in.readString();
        inve_nick_name = in.readString();
        entr_id = in.readString();
        token_num = in.readString();
        inve_remark = in.readString();
        inve_id = in.readString();
        entr_nick_name = in.readString();
        id = in.readString();
        entr_balance_amount = in.readString();
        entr_real_name = in.readString();
        asset_name = in.readString();
        asset_type = in.readInt();
        project_logo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trans_no);
        dest.writeString(create_time);
        dest.writeString(entr_avatar);
        dest.writeString(inve_real_name);
        dest.writeString(inve_balance_amount);
        dest.writeString(asset_id);
        dest.writeString(entr_remak);
        dest.writeString(inve_nick_name);
        dest.writeString(entr_id);
        dest.writeString(token_num);
        dest.writeString(inve_remark);
        dest.writeString(inve_id);
        dest.writeString(entr_nick_name);
        dest.writeString(id);
        dest.writeString(entr_balance_amount);
        dest.writeString(entr_real_name);
        dest.writeString(asset_name);
        dest.writeInt(asset_type);
        dest.writeString(project_logo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PartnerTransBean> CREATOR = new Creator<PartnerTransBean>() {
        @Override
        public PartnerTransBean createFromParcel(Parcel in) {
            return new PartnerTransBean(in);
        }

        @Override
        public PartnerTransBean[] newArray(int size) {
            return new PartnerTransBean[size];
        }
    };

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getEntr_avatar() {
        return entr_avatar;
    }

    public void setEntr_avatar(String entr_avatar) {
        this.entr_avatar = entr_avatar;
    }

    public String getInve_real_name() {
        return inve_real_name;
    }

    public void setInve_real_name(String inve_real_name) {
        this.inve_real_name = inve_real_name;
    }

    public String getInve_balance_amount() {
        return inve_balance_amount;
    }

    public void setInve_balance_amount(String inve_balance_amount) {
        this.inve_balance_amount = inve_balance_amount;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getEntr_remak() {
        return entr_remak;
    }

    public void setEntr_remak(String entr_remak) {
        this.entr_remak = entr_remak;
    }

    public String getInve_nick_name() {
        return inve_nick_name;
    }

    public void setInve_nick_name(String inve_nick_name) {
        this.inve_nick_name = inve_nick_name;
    }

    public String getEntr_id() {
        return entr_id;
    }

    public void setEntr_id(String entr_id) {
        this.entr_id = entr_id;
    }

    public String getToken_num() {
        return token_num;
    }

    public void setToken_num(String token_num) {
        this.token_num = token_num;
    }

    public String getInve_remark() {
        return inve_remark;
    }

    public void setInve_remark(String inve_remark) {
        this.inve_remark = inve_remark;
    }

    public String getInve_id() {
        return inve_id;
    }

    public void setInve_id(String inve_id) {
        this.inve_id = inve_id;
    }

    public String getEntr_nick_name() {
        return entr_nick_name;
    }

    public void setEntr_nick_name(String entr_nick_name) {
        this.entr_nick_name = entr_nick_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntr_balance_amount() {
        return entr_balance_amount;
    }

    public void setEntr_balance_amount(String entr_balance_amount) {
        this.entr_balance_amount = entr_balance_amount;
    }

    public String getEntr_real_name() {
        return entr_real_name;
    }

    public void setEntr_real_name(String entr_real_name) {
        this.entr_real_name = entr_real_name;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public int getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(int asset_type) {
        this.asset_type = asset_type;
    }

    public String getProject_logo() {
        return project_logo;
    }

    public void setProject_logo(String project_logo) {
        this.project_logo = project_logo;
    }

    @Override
    public String toString() {
        return "PartnerTransBean{" +
                "trans_no='" + trans_no + '\'' +
                ", create_time='" + create_time + '\'' +
                ", entr_avatar='" + entr_avatar + '\'' +
                ", inve_real_name='" + inve_real_name + '\'' +
                ", inve_balance_amount='" + inve_balance_amount + '\'' +
                ", asset_id='" + asset_id + '\'' +
                ", entr_remak='" + entr_remak + '\'' +
                ", inve_nick_name='" + inve_nick_name + '\'' +
                ", entr_id='" + entr_id + '\'' +
                ", token_num='" + token_num + '\'' +
                ", inve_remark='" + inve_remark + '\'' +
                ", inve_id='" + inve_id + '\'' +
                ", entr_nick_name='" + entr_nick_name + '\'' +
                ", id='" + id + '\'' +
                ", entr_balance_amount='" + entr_balance_amount + '\'' +
                ", entr_real_name='" + entr_real_name + '\'' +
                ", asset_name='" + asset_name + '\'' +
                ", asset_type=" + asset_type +
                ", project_logo='" + project_logo + '\'' +
                '}';
    }
}
