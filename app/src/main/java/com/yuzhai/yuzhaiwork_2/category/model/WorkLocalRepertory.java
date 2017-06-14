package com.yuzhai.yuzhaiwork_2.category.model;

import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;
import com.yuzhai.yuzhaiwork_2.category.request.WorkRequest;

/**
 * Created by 35429 on 2017/6/3.
 */

public class WorkLocalRepertory implements IWorkModel {
    public final static String IS_FIRST_TIME = "yes";
    public final static String NOT_FIRST_TIME = "no";

    @Override
    public void getWorkData(WorkRequest workRequest, OnRequestResponse<WorkDatas> onRequestResponse) {

    }
}
