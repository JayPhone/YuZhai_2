package com.yuzhai.yuzhaiwork_2.personal_order.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAppliedDatas;

/**
 * Created by 35429 on 2017/6/2.
 */

public interface IOrderAppliedModel extends BaseModel {
    void getAppliedOrderData(String isFirst, OnRequestResponse<OrderAppliedDatas> onRequestResponse);
}
