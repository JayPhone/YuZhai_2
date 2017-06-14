package com.yuzhai.yuzhaiwork_2.category.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;
import com.yuzhai.yuzhaiwork_2.category.request.WorkRequest;

/**
 * Created by 35429 on 2017/6/3.
 */

public interface IWorkModel extends BaseModel {
    void getWorkData(WorkRequest workRequest, OnRequestResponse<WorkDatas> onRequestResponse);
}
