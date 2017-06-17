package com.yuzhai.yuzhaiwork_2.user_data.request;

/**
 * Created by 35429 on 2017/6/14.
 */

public class UserDataRequest {
    private String avatarUrl;

    public UserDataRequest(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "UserDataRequest{" +
                "avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
