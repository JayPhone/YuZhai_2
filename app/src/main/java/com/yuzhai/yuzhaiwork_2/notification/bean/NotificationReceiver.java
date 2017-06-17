package com.yuzhai.yuzhaiwork_2.notification.bean;

/**
 * Created by 35429 on 2017/6/17.
 */

public class NotificationReceiver {
    private String type;
    private String order_id;
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NotificationReceiver{" +
                "type='" + type + '\'' +
                ", order_id='" + order_id + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
