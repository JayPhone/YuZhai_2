package com.yuzhai.yuzhaiwork_2.order_detail.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.ApplyOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.WorkDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.ApplyOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.WorkDetailRequest;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface IWorkDetailModel extends BaseModel {
    void sendWorkDetailRequest(WorkDetailRequest workDetailRequest,
                               OnRequestResponse<WorkDetailResponse> onRequestResponse);

    void sendApplyOrderRequest(ApplyOrderRequest applyOrderRequest,
                               OnRequestResponse<ApplyOrderResponse> orderResponseOnRequestResponse);
}
