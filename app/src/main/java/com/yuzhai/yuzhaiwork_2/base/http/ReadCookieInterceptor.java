package com.yuzhai.yuzhaiwork_2.base.http;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by 35429 on 2017/6/3.
 */

public class ReadCookieInterceptor implements Interceptor {
    private static final String TAG = "ReadCookieInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();
        //获取Cookie
        String cookie = SharePerferenceUtil.getSharePerference(CustomApplication.getInstance()
                .getApplicationContext(), SharePerferenceUtil.FileName.COOKIE)
                .getString(SharePerferenceUtil.Key.COOKIE, "");
        if (!cookie.equals("")) {
            Log.d(TAG, cookie);
            //插入cookie
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build());
    }
}
