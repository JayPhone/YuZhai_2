package com.yuzhai.yuzhaiwork_2.category.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.category.bean.ResumeDatas;
import com.yuzhai.yuzhaiwork_2.category.request.ResumeRequest;

/**
 * Created by 35429 on 2017/6/4.
 */

public interface IResumeModel extends BaseModel {
    void getResumeData(ResumeRequest resumeRequest, OnRequestResponse<ResumeDatas> onRequestResponse);
}
