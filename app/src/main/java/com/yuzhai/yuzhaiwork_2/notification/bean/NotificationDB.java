package com.yuzhai.yuzhaiwork_2.notification.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 35429 on 2017/6/17.
 */

public class NotificationDB extends DataSupport {
    private int id;
    private String title;
    private String description;
    private String type;
    private String date;
    private String order_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "NotificationDB{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", order_id='" + order_id + '\'' +
                '}';
    }
}
