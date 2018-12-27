package com.coop.android.model;

/**
 * Created by MR-Z on 2018/12/18.
 */
public class LoginResponseBean {
    private UserInfo user;
    private String token;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponseBean{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
