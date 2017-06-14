package com.yuzhai.yuzhaiwork_2.base.http;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by 35429 on 2017/6/3.
 */

public class SaveCookieInterceptor implements Interceptor {
    private static final String TAG = "SaveCookieInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        String originalCookie = originalResponse.header("Set-Cookie");
        String cookie = null;
        if (originalCookie != null) {
            cookie = originalCookie.split(";")[0];
        }
        if (cookie != null) {
            Log.d(TAG, cookie);
            //保存cookie
            SharePerferenceUtil.getSharePerference(CustomApplication.getInstance()
                    .getApplicationContext(), SharePerferenceUtil.FileName.COOKIE)
                    .edit()
                    .putString(SharePerferenceUtil.Key.COOKIE, cookie)
                    .apply();
        }
        return originalResponse;
    }
}
