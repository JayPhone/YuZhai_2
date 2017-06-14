package com.yuzhai.yuzhaiwork_2.order_detail.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelPublishedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderPublishedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelPublishedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderPublishedDetailRequest;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface IOrderPublishedDetailModel extends BaseModel {
    void sendOrderPublishedDetailRequest(OrderPublishedDetailRequest orderPublishedDetailRequest,
                                         OnRequestResponse<OrderPublishedDetailResponse> onRequestResponse);

    void sendCancelPublishedOrderRequest(CancelPublishedOrderRequest cancelPublishedOrderRequest,
                                         OnRequestResponse<CancelPublishedOrderResponse> onRequestResponse);
}
