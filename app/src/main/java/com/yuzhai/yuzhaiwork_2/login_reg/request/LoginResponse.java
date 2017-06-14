package com.yuzhai.yuzhaiwork_2.login_reg.request;

/**
 * Created by 35429 on 2017/5/19.
 */
public class LoginResponse {
    //返回码
    private String code;
    //用户名
    private String user_name;
    //用户头像路径
    private String user_head_url;

    public LoginResponse(String code, String user_name, String user_head_url) {
        this.code = code;
        this.user_name = user_name;
        this.user_head_url = user_head_url;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_head_url(String user_head_url) {
        this.user_head_url = user_head_url;
    }

    public String getCode() {
        return code;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_head_url() {
        return user_head_url;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "code='" + code + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_head_url='" + user_head_url + '\'' +
                '}';
    }
}
