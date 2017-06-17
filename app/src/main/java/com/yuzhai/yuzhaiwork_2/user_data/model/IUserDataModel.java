package com.yuzhai.yuzhaiwork_2.user_data.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.user_data.bean.AgreeAcceptOrderResponse;
import com.yuzhai.yuzhaiwork_2.user_data.bean.UserDataResponse;
import com.yuzhai.yuzhaiwork_2.user_data.request.AgreeAcceptOrderRequest;
import com.yuzhai.yuzhaiwork_2.user_data.request.UserDataRequest;

/**
 * Created by 35429 on 2017/6/14.
 */

public interface IUserDataModel extends BaseModel {
    void sendUserDataRequest(UserDataRequest userDataRequest, OnRequestResponse<UserDataResponse> onRequestResponse);

    void sendAgreeAcceptOrderRequest(AgreeAcceptOrderRequest agreeAcceptOrderRequest,
                                     OnRequestResponse<AgreeAcceptOrderResponse> onRequestResponse);
}
