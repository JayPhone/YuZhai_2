package com.yuzhai.yuzhaiwork_2.login_reg.request;

/**
 * Created by 35429 on 2017/5/19.
 */

public class LoginRequest {
    private String userPhone;
    private String userPsw;
    private String regID;

    public LoginRequest(String userPhone, String userPsw, String regID) {
        this.userPhone = userPhone;
        this.userPsw = userPsw;
        this.regID = regID;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getRegID() {
        return regID;
    }

    public void setRegID(String regID) {
        this.regID = regID;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userPhone='" + userPhone + '\'' +
                ", userPsw='" + userPsw + '\'' +
                ", regID='" + regID + '\'' +
                '}';
    }
}
