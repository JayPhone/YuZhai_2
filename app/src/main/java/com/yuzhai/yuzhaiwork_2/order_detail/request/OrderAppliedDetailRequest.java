package com.yuzhai.yuzhaiwork_2.order_detail.request;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAppliedDetailRequest {
    private String orderId;

    public OrderAppliedDetailRequest(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderAppliedDetailRequest{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
