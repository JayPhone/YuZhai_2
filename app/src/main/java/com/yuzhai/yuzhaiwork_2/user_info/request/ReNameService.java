package com.yuzhai.yuzhaiwork_2.user_info.request;

import com.yuzhai.yuzhaiwork_2.user_info.bean.ReNameResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 35429 on 2017/6/12.
 */

public interface ReNameService {
    @FormUrlEncoded
    @POST("rename")
    Observable<ReNameResponse> reName(@Field("name") String newName);
}
