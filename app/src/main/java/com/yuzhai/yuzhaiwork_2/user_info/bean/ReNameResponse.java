package com.yuzhai.yuzhaiwork_2.user_info.bean;

/**
 * Created by 35429 on 2017/6/12.
 */

public class ReNameResponse {
    private String code;

    public ReNameResponse(String code) {
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
        return "ReNameResponse{" +
                "code='" + code + '\'' +
                '}';
    }
}
