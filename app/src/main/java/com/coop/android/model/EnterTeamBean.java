package com.coop.android.model;


/**
 * Created by MR-Z on 2018/12/18.
 */
public class EnterTeamBean {
    private String nick_name;
    private String mobile_no;
    private String index;
    private String real_name;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    @Override
    public String toString() {
        return "EnterTeamBean{" +
                "nick_name='" + nick_name + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", index='" + index + '\'' +
                ", real_name='" + real_name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
