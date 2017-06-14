package com.yuzhai.yuzhaiwork_2.user_info.request;

/**
 * Created by 35429 on 2017/6/11.
 */

public class UploadAvaterRequest {
    private String imagePath;

    public UploadAvaterRequest(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "UploadAvaterRequest{" +
                "imagePath='" + imagePath + '\'' +
                '}';
    }
}
