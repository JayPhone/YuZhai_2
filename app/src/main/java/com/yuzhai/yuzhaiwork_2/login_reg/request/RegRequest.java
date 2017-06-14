package com.yuzhai.yuzhaiwork_2.login_reg.request;

/**
 * Created by 35429 on 2017/5/22.
 */

public class RegRequest {
    private String userPhone;
    private String validateCode;
    private String userPsw;
    private String cfmPsw;

    public RegRequest(String userPhone, String validateCode, String userPsw, String cfmPsw) {
        this.userPhone = userPhone;
        this.validateCode = validateCode;
        this.userPsw = userPsw;
        this.cfmPsw = cfmPsw;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getCfmPsw() {
        return cfmPsw;
    }

    public void setCfmPsw(String cfmPsw) {
        this.cfmPsw = cfmPsw;
    }
}
