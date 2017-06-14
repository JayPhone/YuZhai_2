package com.yuzhai.yuzhaiwork_2.resume.model;

import android.widget.SimpleAdapter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.resume.bean.PublishResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.bean.QueryResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.request.PublishResumeRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/6/9.
 */

public interface IPublishResumeModel extends BaseModel {
    void getSexSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse);

    void getTypeSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse);

    void getEducationSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse);

    void sendPublishResumeRequest(PublishResumeRequest publishResumeRequest,
                                  OnRequestResponse<PublishResumeResponse> onRequestResponse);

    void sendQueryResumeRequest(OnRequestResponse<QueryResumeResponse> onRequestResponse);
}
