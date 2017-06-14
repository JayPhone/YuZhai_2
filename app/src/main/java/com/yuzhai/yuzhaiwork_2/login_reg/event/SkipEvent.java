package com.yuzhai.yuzhaiwork_2.login_reg.event;

/**
 * Created by 35429 on 2017/6/4.
 */

public class SkipEvent {
    //发起跳转Fragment来源
    public static final String LOGIN_EVENT = "login_event";
    public static final String FGT_PSW_EVENT = "fgt_psw_event";
    public static final String REG_EVENT = "reg_ecent";

    //要跳转的页面
    public static final String LOGIN_SKIP = "login_skip";
    public static final String REG_SKIP = "reg_skip";
    public static final String FGT_PSW_SKIP = "fgt_psw_skip";

    //发起跳转来源
    private String from;
    //跳转页面
    private String to;

    public SkipEvent(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
