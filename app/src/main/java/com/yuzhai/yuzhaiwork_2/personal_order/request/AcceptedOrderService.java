package com.yuzhai.yuzhaiwork_2.personal_order.request;

import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAcceptedDatas;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 35429 on 2017/6/8.
 */

public interface AcceptedOrderService {
    @GET("lookupreceive")
    Observable<OrderAcceptedDatas> getAcceptedOrders(@Query("first") String first);
}
