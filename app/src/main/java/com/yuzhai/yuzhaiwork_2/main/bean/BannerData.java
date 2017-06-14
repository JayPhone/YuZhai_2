package com.yuzhai.yuzhaiwork_2.main.bean;

/**
 * Created by 35429 on 2017/5/31.
 */

public class BannerData {
    private int imageId;

    public BannerData(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "BannerData{" +
                "imageId=" + imageId +
                '}';
    }
}
