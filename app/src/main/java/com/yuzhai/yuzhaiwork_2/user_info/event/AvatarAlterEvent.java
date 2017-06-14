package com.yuzhai.yuzhaiwork_2.user_info.event;

/**
 * Created by 35429 on 2017/6/12.
 */

public class AvatarAlterEvent {
    private String userAvatarUrl;

    public AvatarAlterEvent(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    @Override
    public String toString() {
        return "AvatarAlterEvent{" +
                "userAvatarUrl='" + userAvatarUrl + '\'' +
                '}';
    }
}
