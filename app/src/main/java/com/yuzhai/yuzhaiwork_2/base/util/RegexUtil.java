package com.yuzhai.yuzhaiwork_2.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 35429 on 2017/5/18.
 */

public class RegexUtil {
    private static final String PHONE_REGEX_EXP = "^1(3|4|5|7|8)\\d{9}$";
    private static final String PSW_REGEX_EXP = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    private static final String VALIDATE_EXP = "^\\d{6}$";

    /**
     * 用正则表达式校验数据
     *
     * @param exp 校验规则
     * @param str 校验字符
     * @return 是否校验成功
     */
    private static boolean regex(String exp, String str) {
        Pattern p = Pattern.compile(exp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 校验手机号码
     *
     * @param telephone 手机号码
     * @return 通过校验
     */
    public static boolean regexPhone(String telephone) {
        return regex(PHONE_REGEX_EXP, telephone);
    }

    /**
     * 校验6-16位含数字和字母的密码
     *
     * @param password 密码
     * @return 通过校验
     */
    public static boolean regexPsw(String password) {
        return regex(PSW_REGEX_EXP, password);
    }

    /**
     * 校验6位数字
     *
     * @param code 验证码
     * @return 通过校验
     */
    public static boolean regexValidateCode(String code) {
        return regex(VALIDATE_EXP, code);
    }
}
