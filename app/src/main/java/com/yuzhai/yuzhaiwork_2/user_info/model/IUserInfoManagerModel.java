package com.yuzhai.yuzhaiwork_2.user_info.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.user_info.bean.ExitLoginResponse;
import com.yuzhai.yuzhaiwork_2.user_info.bean.ReNameResponse;
import com.yuzhai.yuzhaiwork_2.user_info.bean.UploadAvaterResponse;
import com.yuzhai.yuzhaiwork_2.user_info.request.ReNameRequest;

import java.io.File;

/**
 * Created by 35429 on 2017/6/9.
 */

public interface IUserInfoManagerModel extends BaseModel {
    void sendExitLoginRequest(OnRequestResponse<ExitLoginResponse> onRequestResponse);

    void sendUploadAvaterRequest(File file, OnRequestResponse<UploadAvaterResponse> onRequestResponse);

    void sendReNameRequest(ReNameRequest reNameRequest, OnRequestResponse<ReNameResponse> onRequestResponse);
}
