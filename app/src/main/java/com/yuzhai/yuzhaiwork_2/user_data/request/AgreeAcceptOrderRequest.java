package com.yuzhai.yuzhaiwork_2.user_data.request;

/**
 * Created by 35429 on 2017/6/14.
 */

public class AgreeAcceptOrderRequest {
    private String orderId;
    private String bidderId;

    public AgreeAcceptOrderRequest(String orderId, String bidderId) {
        this.orderId = orderId;
        this.bidderId = bidderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    @Override
    public String toString() {
        return "AgreeAcceptOrderRequest{" +
                "orderId='" + orderId + '\'' +
                ", bidderId='" + bidderId + '\'' +
                '}';
    }
}
