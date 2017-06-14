package com.yuzhai.yuzhaiwork_2.order_detail.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelAcceptedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAcceptedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAcceptedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAcceptedDetailRequest;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface IOrderAcceptedDetailModel extends BaseModel {
    void sendOrderAcceptedDetailRequest(OrderAcceptedDetailRequest orderAcceptedDetailRequest,
                                       OnRequestResponse<OrderAcceptedDetailResponse> onRequestResponse);

    void sendCancelAcceptedOrderRequest(CancelAcceptedOrderRequest cancelAcceptedOrderRequest,
                                       OnRequestResponse<CancelAcceptedOrderResponse> onRequestResponse);
}
