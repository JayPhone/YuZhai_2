package com.yuzhai.yuzhaiwork_2.notification.request;

/**
 * Created by 35429 on 2017/6/17.
 */

public class GetNotificationRequest {
    private int limit;

    public GetNotificationRequest(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "GetNotificationRequest{" +
                "limit=" + limit +
                '}';
    }
}
