package com.yuzhai.yuzhaiwork_2.category.request;


import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 35429 on 2017/6/8.
 */

public interface WorkService {
    @FormUrlEncoded
    @POST("lookupbytype")
    Observable<WorkDatas> getWorkData(@Field("itemType") String itemType,
                                      @Field("first") String first);
}
