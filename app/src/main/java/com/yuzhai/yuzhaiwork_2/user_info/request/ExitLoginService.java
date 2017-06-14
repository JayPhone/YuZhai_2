package com.yuzhai.yuzhaiwork_2.user_info.request;

import com.yuzhai.yuzhaiwork_2.user_info.bean.ExitLoginResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by 35429 on 2017/6/9.
 */

public interface ExitLoginService {
    @GET("quit")
    Observable<ExitLoginResponse> exitLogin();
}
