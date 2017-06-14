package com.yuzhai.yuzhaiwork_2.login_reg.request;

/**
 * Created by 35429 on 2017/5/22.
 */

public class ValidateRequest {
    private String userPhone;
    private String validateCode;

    public ValidateRequest(String userPhone, String validateCode) {
        this.userPhone = userPhone;
        this.validateCode = validateCode;
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
}
