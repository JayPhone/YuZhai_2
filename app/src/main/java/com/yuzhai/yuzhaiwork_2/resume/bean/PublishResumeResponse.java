package com.yuzhai.yuzhaiwork_2.resume.bean;

/**
 * Created by 35429 on 2017/6/9.
 */

public class PublishResumeResponse {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PublishResumeResponse{" +
                "code='" + code + '\'' +
                '}';
    }
}
