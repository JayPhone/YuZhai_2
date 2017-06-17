package com.yuzhai.yuzhaiwork_2.user_data.request;

import com.yuzhai.yuzhaiwork_2.user_data.bean.UserDataResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 35429 on 2017/6/14.
 */

public interface UserDataService {
    @FormUrlEncoded
    @POST("lookupbyavatar")
    Observable<UserDataResponse> getUserData(@Field("avatar") String avatar);
}
