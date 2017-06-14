package com.yuzhai.yuzhaiwork_2.personal_order.request;

import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAppliedDatas;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 35429 on 2017/6/8.
 */

public interface AppliedOrderService {
    @GET("lookupapply")
    Observable<OrderAppliedDatas> getAppliedOrders(@Query("first") String first);
}
