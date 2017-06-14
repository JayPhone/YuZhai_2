package com.yuzhai.yuzhaiwork_2.base.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/6/3.
 */

public class SharePerferenceUtil {
    public static class FileName {
        public static String COOKIE = "cookie";
        public static String USER_INFO = "userinfo";
    }

    public static class Key {
        public static String COOKIE = "cookie";
        public static String USERNAME = "username";
        public static String USERPSW = "userpsw";
    }


    public static SharedPreferences getSharePerference(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
}
