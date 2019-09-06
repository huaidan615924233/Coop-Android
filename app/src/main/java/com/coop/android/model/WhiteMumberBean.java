package com.coop.android.model;

/**
 * Created by MR-Z on 2019/3/6.
 */
public class WhiteMumberBean {
    private String inve_role_id;
    private String nick_name;
    private String mobile_no;
    private int index;
    private String real_name;
    private String state;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInve_role_id() {
        return inve_role_id;
    }

    public void setInve_role_id(String inve_role_id) {
        this.inve_role_id = inve_role_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "WhiteMumberBean{" +
                "inve_role_id='" + inve_role_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", index=" + index +
                ", real_name='" + real_name + '\'' +
                ", state='" + state + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
