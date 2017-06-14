package com.yuzhai.yuzhaiwork_2.personal_order.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderPublishedDatas;

/**
 * Created by 35429 on 2017/6/1.
 */

public interface IOrderPublishedModel extends BaseModel {
    void getPublishedOrderData(String isFirst, OnRequestResponse<OrderPublishedDatas> onRequestResponse);
}
