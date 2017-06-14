package com.yuzhai.yuzhaiwork_2.collection.bean;

/**
 * Created by 35429 on 2017/5/29.
 */

public class CollectionData {
    private String title;
    private String type;
    private Integer image;
    private String date;

    public CollectionData(String title, String type, Integer image, String date) {
        this.title = title;
        this.type = type;
        this.image = image;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CollectionData{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", image=" + image +
                ", date='" + date + '\'' +
                '}';
    }
}
