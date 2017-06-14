package com.yuzhai.yuzhaiwork_2.order_detail.request;

import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAcceptedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.WorkDetailResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface OrderAcceptedDetailService {
    @FormUrlEncoded
    @POST("detailedorder")
    Observable<OrderAcceptedDetailResponse> getWorkDetailData(@Field("orderID") String orderId);
}
