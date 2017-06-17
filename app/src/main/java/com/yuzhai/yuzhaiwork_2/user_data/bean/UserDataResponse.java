package com.yuzhai.yuzhaiwork_2.user_data.bean;

/**
 * Created by 35429 on 2017/6/14.
 */

public class UserDataResponse {
    private String avatar;
    private String user_phone;
    private String user_name;
    private String authen;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserPhone() {
        return user_phone;
    }

    public void setUserPhone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getAuthen() {
        return authen;
    }

    public void setAuthen(String authen) {
        this.authen = authen;
    }

    @Override
    public String toString() {
        return "UserDataResponse{" +
                "avatar='" + avatar + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_name='" + user_name + '\'' +
                ", authen='" + authen + '\'' +
                '}';
    }
}
