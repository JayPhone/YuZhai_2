package com.yuzhai.yuzhaiwork_2.resume.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.resume.bean.QueryResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.request.PublishResumeRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/6/9.
 */

public interface PublishResumeContact {
    interface View extends BaseView<Presenter> {
        void setSexSpinnerData(List<Map<String, String>> sexSpinnerData);

        void setTypeSpinnerData(List<Map<String, String>> typeSpinnerData);

        void setEducationSpinnerData(List<Map<String, String>> educationSpinnerData);

        void setResumeData(QueryResumeResponse queryResumeResponse);
    }

    interface Presenter extends BasePresenter {
        boolean checkResumeInput(PublishResumeRequest publishResumeRequest);

        void getSexSpinnerData();

        void getTypeSpinnerData();

        void getEducationSpinnerData();

        void sendPublishResumeRequest(PublishResumeRequest publishResumeRequest);

        void sendQueryResumeRequest();
    }
}
