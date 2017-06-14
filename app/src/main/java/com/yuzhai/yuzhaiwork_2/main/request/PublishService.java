package com.yuzhai.yuzhaiwork_2.main.request;

import com.yuzhai.yuzhaiwork_2.main.bean.PublishResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by 35429 on 2017/6/12.
 */

public interface PublishService {
    @Multipart
    @POST("publishtask")
    Observable<PublishResponse> publishOrder(@Part("title") RequestBody title,
                                             @Part("description") RequestBody description,
                                             @Part("type") RequestBody type,
                                             @Part("deadline") RequestBody deadline,
                                             @Part("tel") RequestBody tel,
                                             @Part("reward") RequestBody reward,
                                             @PartMap() Map<String, RequestBody> bodyMap);
}
