package com.yuzhai.yuzhaiwork_2.user_data.request;

import com.yuzhai.yuzhaiwork_2.user_data.bean.AgreeAcceptOrderResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 35429 on 2017/6/14.
 */

public interface AgreeAcceptOrderService {
    @FormUrlEncoded
    @POST("decidebidder")
    Observable<AgreeAcceptOrderResponse> agreeAcceptOrder(@Field("orderID") String orderId,
                                                          @Field("bidderID") String bidderId);
}
