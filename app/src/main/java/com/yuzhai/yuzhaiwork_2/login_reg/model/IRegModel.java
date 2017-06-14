package com.yuzhai.yuzhaiwork_2.login_reg.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegResponse;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateResponse;

/**
 * Created by 35429 on 2017/5/23.
 */

public interface IRegModel extends BaseModel {
    void sendValidateRequest(ValidateRequest validateRequest, OnRequestResponse<ValidateResponse> onRequestResponse);

    void sendRegRequest(RegRequest regRequest, OnRequestResponse<RegResponse> onRequestResponse);

    void sendValiadateConfirmRequest(RegRequest regRequest, OnRequestResponse<ValidateResponse> onRequestResponse);
}
