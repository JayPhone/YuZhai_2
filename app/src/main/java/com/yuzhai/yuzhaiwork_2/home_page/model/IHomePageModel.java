package com.yuzhai.yuzhaiwork_2.home_page.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.home_page.bean.HomePageData;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

/**
 * Created by 35429 on 2017/6/6.
 */

public interface IHomePageModel extends BaseModel {
    void getHomePageData(OnRequestResponse<HomePageData> onRequestResponse);

    void sentLoginRequest(LoginRequest loginRequest, OnRequestResponse<LoginResponse> onRequestResponse);
}
