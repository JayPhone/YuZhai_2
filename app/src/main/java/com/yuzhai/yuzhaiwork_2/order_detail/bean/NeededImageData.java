package com.yuzhai.yuzhaiwork_2.order_detail.bean;

/**
 * Created by 35429 on 2017/6/13.
 */

public class NeededImageData {
    private String imageUrl;

    public NeededImageData(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "NeededImageData{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
