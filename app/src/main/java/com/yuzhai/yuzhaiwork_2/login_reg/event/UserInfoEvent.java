package com.yuzhai.yuzhaiwork_2.login_reg.event;

/**
 * Created by 35429 on 2017/6/5.
 */

public class UserInfoEvent {
    //用户名
    private String userName;
    //用户头像路径
    private String userAvaterUrl;

    public UserInfoEvent(String userName, String userAvaterUrl) {
        this.userName = userName;
        this.userAvaterUrl = userAvaterUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvaterUrl() {
        return userAvaterUrl;
    }

    public void setUserAvaterUrl(String userAvaterUrl) {
        this.userAvaterUrl = userAvaterUrl;
    }

    @Override
    public String toString() {
        return "UserInfoEvent{" +
                "userName='" + userName + '\'' +
                ", userAvaterUrl='" + userAvaterUrl + '\'' +
                '}';
    }
}
