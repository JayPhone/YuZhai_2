package com.yuzhai.yuzhaiwork_2.main.event;

/**
 * Created by 35429 on 2017/6/12.
 */

public class UserDetailInfoEvent {
    private String userAvatarUrl;
    private String userName;
    private String userPhone;
    private String realName;

    public UserDetailInfoEvent(String userAvatarUrl, String userName, String userPhone, String realName) {
        this.userAvatarUrl = userAvatarUrl;
        this.userName = userName;
        this.userPhone = userPhone;
        this.realName = realName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "UserDetailInfoEvent{" +
                "userAvatarUrl='" + userAvatarUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
