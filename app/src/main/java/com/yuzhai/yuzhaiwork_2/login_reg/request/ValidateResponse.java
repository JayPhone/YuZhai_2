package com.yuzhai.yuzhaiwork_2.login_reg.request;

/**
 * Created by 35429 on 2017/5/23.
 */

public class ValidateResponse {
    private String code;

    public ValidateResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ValidateResponse{" +
                "code='" + code + '\'' +
                '}';
    }
}
