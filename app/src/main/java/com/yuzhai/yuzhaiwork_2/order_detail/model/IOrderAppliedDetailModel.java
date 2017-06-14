package com.yuzhai.yuzhaiwork_2.order_detail.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelAppliedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAppliedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAppliedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAppliedDetailRequest;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface IOrderAppliedDetailModel extends BaseModel {
    void sendOrderAppliedDetailRequest(OrderAppliedDetailRequest orderAppliedDetailRequest,
                                       OnRequestResponse<OrderAppliedDetailResponse> onRequestResponse);

    void sendCancelAppliedOrderRequest(CancelAppliedOrderRequest cancelAppliedOrderRequest,
                                       OnRequestResponse<CancelAppliedOrderResponse> onRequestResponse);
}
