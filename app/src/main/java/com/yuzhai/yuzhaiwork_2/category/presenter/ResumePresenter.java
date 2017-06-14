package com.yuzhai.yuzhaiwork_2.category.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.category.bean.ResumeDatas;
import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;
import com.yuzhai.yuzhaiwork_2.category.contact.ResumeContact;
import com.yuzhai.yuzhaiwork_2.category.model.IResumeModel;
import com.yuzhai.yuzhaiwork_2.category.model.ResumeLocalRepertory;
import com.yuzhai.yuzhaiwork_2.category.model.ResumeRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.category.model.WorkRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.category.request.ResumeRequest;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/3.
 */

public class ResumePresenter implements ResumeContact.Presenter {
    private WeakReference<ResumeContact.View> mResumeView;
    private IResumeModel mResumeModel = new ResumeRemoteRepertory();

    public ResumePresenter(ResumeContact.View resumeView) {
        this.mResumeView = new WeakReference<>(resumeView);
        this.mResumeView.get().setPresenter(this);
    }

    @Override
    public void start() {
        mResumeView.get().startLoading();
    }

    @Override
    public void clear() {
        if (mResumeView.get() != null) {
            mResumeView.clear();
        }
    }

    @Override
    public void getResumeData(ResumeRequest resumeRequest) {
        mResumeModel.getResumeData(resumeRequest, new BaseModel.OnRequestResponse<ResumeDatas>() {
            @Override
            public void onSuccess(ResumeDatas resumeDatas) {
                if (mResumeView.get().isActive()) {
                    mResumeView.get().setResumeData(resumeDatas);

                    mResumeView.get().dimissLoading();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mResumeView.get().isActive()) {
                    mResumeView.get().showToast("数据加载错误");
                    mResumeView.get().dimissLoading();
                }
            }
        });
    }
}
