package com.yuzhai.yuzhaiwork_2.login_reg.request;

/**
 * Created by 35429 on 2017/5/24.
 */

public class AlterPswRequest {
    private String userPhone;
    private String validateCode;
    private String psw1;
    private String psw2;

    public AlterPswRequest(String userPhone, String validateCode, String psw1, String psw2) {
        this.userPhone = userPhone;
        this.validateCode = validateCode;
        this.psw1 = psw1;
        this.psw2 = psw2;
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

    public String getPsw1() {
        return psw1;
    }

    public void setPsw1(String psw1) {
        this.psw1 = psw1;
    }

    public String getPsw2() {
        return psw2;
    }

    public void setPsw2(String psw2) {
        this.psw2 = psw2;
    }
}
