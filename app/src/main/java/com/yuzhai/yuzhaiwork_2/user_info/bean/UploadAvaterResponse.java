package com.yuzhai.yuzhaiwork_2.user_info.bean;

/**
 * Created by 35429 on 2017/6/11.
 */

public class UploadAvaterResponse {
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UploadAvaterResponse{" +
                "avatar='" + avatar + '\'' +
                '}';
    }
}
