package com.yuzhai.yuzhaiwork_2.login_reg.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

/**
 * Created by 35429 on 2017/5/22.
 */

public interface ILoginModel extends BaseModel {
    void setLoginRequest(LoginRequest loginRequest, final OnRequestResponse<LoginResponse> onRequestResponse);
}
