package com.yuzhai.yuzhaiwork_2.base.event;

/**
 * Created by 35429 on 2017/6/9.
 */

public class LoginEvent {
    private boolean login;

    public LoginEvent(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "LoginEvent{" +
                "login=" + login +
                '}';
    }
}
