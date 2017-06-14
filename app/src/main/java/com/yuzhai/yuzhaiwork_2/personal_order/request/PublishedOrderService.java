package com.yuzhai.yuzhaiwork_2.personal_order.request;

import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderPublishedDatas;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 35429 on 2017/6/1.
 */

public interface PublishedOrderService {
    @GET("lookuppublished")
    Observable<OrderPublishedDatas> getPublishedOrders(@Query("first") String first);
}
