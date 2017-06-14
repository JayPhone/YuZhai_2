package com.yuzhai.yuzhaiwork_2.main.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.main.bean.BannerData;
import com.yuzhai.yuzhaiwork_2.main.bean.CategoryData;

import java.util.List;

/**
 * Created by 35429 on 2017/6/10.
 */

public interface IHomeModel extends BaseModel {
    void getBannerData(OnRequestResponse<List<BannerData>> onRequestResponse);

    void getCategoryData(OnRequestResponse<List<CategoryData>> onRequestResponse);
}
