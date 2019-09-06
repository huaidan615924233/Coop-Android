package com.coop.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class TransDetailBean implements Parcelable {
    private String trans_no;
    private String transaction_id;
    private String trans_b_height;
    private String token_num;
    private String create_time;
    private String inve_balance_amount;
    private String id;
    private String asset_id;
    private String trans_hash;
    private String entr_balance_amount;
    private String trans_b_timestamp;
    private String entr_real_name;
    private String inve_real_name;
    private String entr_remak;
    private String inve_nick_name;
    private String inve_remark;
    private String entr_nick_name;
    private int asset_type;
    private String asset_name;
    private List<AgreementBean> file_list;

    public TransDetailBean() {
    }

    protected TransDetailBean(Parcel in) {
        trans_no = in.readString();
        transaction_id = in.readString();
        trans_b_height = in.readString();
        token_num = in.readString();
        create_time = in.readString();
        inve_balance_amount = in.readString();
        id = in.readString();
        asset_id = in.readString();
        trans_hash = in.readString();
        entr_balance_amount = in.readString();
        trans_b_timestamp = in.readString();
        entr_real_name = in.readString();
        inve_real_name = in.readString();
        entr_remak = in.readString();
        inve_nick_name = in.readString();
        inve_remark = in.readString();
        entr_nick_name = in.readString();
        asset_type = in.readInt();
        asset_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trans_no);
        dest.writeString(transaction_id);
        dest.writeString(trans_b_height);
        dest.writeString(token_num);
        dest.writeString(create_time);
        dest.writeString(inve_balance_amount);
        dest.writeString(id);
        dest.writeString(asset_id);
        dest.writeString(trans_hash);
        dest.writeString(entr_balance_amount);
        dest.writeString(trans_b_timestamp);
        dest.writeString(entr_real_name);
        dest.writeString(inve_real_name);
        dest.writeString(entr_remak);
        dest.writeString(inve_nick_name);
        dest.writeString(inve_remark);
        dest.writeString(entr_nick_name);
        dest.writeInt(asset_type);
        dest.writeString(asset_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransDetailBean> CREATOR = new Creator<TransDetailBean>() {
        @Override
        public TransDetailBean createFromParcel(Parcel in) {
            return new TransDetailBean(in);
        }

        @Override
        public TransDetailBean[] newArray(int size) {
            return new TransDetailBean[size];
        }
    };

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTrans_b_height() {
        return trans_b_height;
    }

    public void setTrans_b_height(String trans_b_height) {
        this.trans_b_height = trans_b_height;
    }

    public String getToken_num() {
        return token_num;
    }

    public void setToken_num(String token_num) {
        this.token_num = token_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getInve_balance_amount() {
        return inve_balance_amount;
    }

    public void setInve_balance_amount(String inve_balance_amount) {
        this.inve_balance_amount = inve_balance_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getTrans_hash() {
        return trans_hash;
    }

    public void setTrans_hash(String trans_hash) {
        this.trans_hash = trans_hash;
    }

    public String getEntr_balance_amount() {
        return entr_balance_amount;
    }

    public void setEntr_balance_amount(String entr_balance_amount) {
        this.entr_balance_amount = entr_balance_amount;
    }

    public String getTrans_b_timestamp() {
        return trans_b_timestamp;
    }

    public void setTrans_b_timestamp(String trans_b_timestamp) {
        this.trans_b_timestamp = trans_b_timestamp;
    }

    public String getEntr_real_name() {
        return entr_real_name;
    }

    public void setEntr_real_name(String entr_real_name) {
        this.entr_real_name = entr_real_name;
    }

    public String getInve_real_name() {
        return inve_real_name;
    }

    public void setInve_real_name(String inve_real_name) {
        this.inve_real_name = inve_real_name;
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

    public String getInve_remark() {
        return inve_remark;
    }

    public void setInve_remark(String inve_remark) {
        this.inve_remark = inve_remark;
    }

    public String getEntr_nick_name() {
        return entr_nick_name;
    }

    public void setEntr_nick_name(String entr_nick_name) {
        this.entr_nick_name = entr_nick_name;
    }

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

    public List<AgreementBean> getFile_list() {
        return file_list;
    }

    public void setFile_list(List<AgreementBean> file_list) {
        this.file_list = file_list;
    }

    @Override
    public String toString() {
        return "TransDetailBean{" +
                "trans_no='" + trans_no + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", trans_b_height='" + trans_b_height + '\'' +
                ", token_num='" + token_num + '\'' +
                ", create_time='" + create_time + '\'' +
                ", inve_balance_amount='" + inve_balance_amount + '\'' +
                ", id='" + id + '\'' +
                ", asset_id='" + asset_id + '\'' +
                ", trans_hash='" + trans_hash + '\'' +
                ", entr_balance_amount='" + entr_balance_amount + '\'' +
                ", trans_b_timestamp='" + trans_b_timestamp + '\'' +
                ", entr_real_name='" + entr_real_name + '\'' +
                ", inve_real_name='" + inve_real_name + '\'' +
                ", entr_remak='" + entr_remak + '\'' +
                ", inve_nick_name='" + inve_nick_name + '\'' +
                ", inve_remark='" + inve_remark + '\'' +
                ", entr_nick_name='" + entr_nick_name + '\'' +
                ", asset_type=" + asset_type +
                ", asset_name='" + asset_name + '\'' +
                ", file_list=" + file_list +
                '}';
    }
}
