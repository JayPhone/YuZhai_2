package com.yuzhai.yuzhaiwork_2.main.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.main.bean.PublishResponse;
import com.yuzhai.yuzhaiwork_2.main.request.PublishRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/6/10.
 */

public interface IPublishModel extends BaseModel {
    void getTypeSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse);

    void getLimitSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse);

    void sendPublishOrderRequest(PublishRequest publishRequest, OnRequestResponse<PublishResponse> onRequestResponse);
}
