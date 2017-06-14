package com.yuzhai.yuzhaiwork_2.category.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;
import com.yuzhai.yuzhaiwork_2.category.request.WorkRequest;

/**
 * Created by 35429 on 2017/6/3.
 */

public interface WorkContact {
    interface View extends BaseView<Presenter> {
        void setWorkData(WorkDatas workDatas);

        void dimissLoading();

        void startLoading();

        String getCategory();
    }

    interface Presenter extends BasePresenter {
        void getWorkData(WorkRequest workRequest);
    }
}
