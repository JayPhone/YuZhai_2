package com.yuzhai.yuzhaiwork_2.category.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.category.bean.ResumeDatas;
import com.yuzhai.yuzhaiwork_2.category.request.ResumeRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.contact.LoginContact;

/**
 * Created by 35429 on 2017/6/3.
 */

public interface ResumeContact {
    interface View extends BaseView<Presenter> {
        void setResumeData(ResumeDatas resumeDatas);

        void getResumeData();

        void dimissLoading();

        void startLoading();
    }

    interface Presenter extends BasePresenter {
        void getResumeData(ResumeRequest resumeRequest);
    }
}
