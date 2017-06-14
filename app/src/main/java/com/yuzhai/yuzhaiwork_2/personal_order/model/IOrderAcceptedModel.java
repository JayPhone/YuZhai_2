package com.yuzhai.yuzhaiwork_2.personal_order.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAcceptedDatas;

/**
 * Created by 35429 on 2017/6/2.
 */

public interface IOrderAcceptedModel extends BaseModel {
    void getAcceptedOrderData(String isFirst, OnRequestResponse<OrderAcceptedDatas> onRequestResponse);
}
