package com.yuzhai.yuzhaiwork_2.base.http;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 35429 on 2017/6/7.
 */

public class AcceptInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder()
                .header("Accept", "text/html,application/xhtml+xml,application/json,application/xml;image/webp,*/*;");
        return chain.proceed(builder.build());
    }
}
