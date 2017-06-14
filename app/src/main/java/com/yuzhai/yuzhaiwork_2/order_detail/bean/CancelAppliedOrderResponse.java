package com.yuzhai.yuzhaiwork_2.order_detail.bean;

/**
 * Created by 35429 on 2017/6/13.
 */

public class CancelAppliedOrderResponse {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CancelAppliedOrderResponse{" +
                "code='" + code + '\'' +
                '}';
    }
}
