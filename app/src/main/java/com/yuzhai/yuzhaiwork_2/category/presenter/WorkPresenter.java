package com.yuzhai.yuzhaiwork_2.category.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;
import com.yuzhai.yuzhaiwork_2.category.contact.WorkContact;
import com.yuzhai.yuzhaiwork_2.category.model.IWorkModel;
import com.yuzhai.yuzhaiwork_2.category.model.WorkLocalRepertory;
import com.yuzhai.yuzhaiwork_2.category.model.WorkRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.category.request.WorkRequest;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/3.
 */

public class WorkPresenter implements WorkContact.Presenter {
    private WeakReference<WorkContact.View> mWorkView;
    private IWorkModel mWorkModel = new WorkRemoteRepertory();

    public WorkPresenter(WorkContact.View workFragment) {
        mWorkView = new WeakReference<>(workFragment);
        mWorkView.get().setPresenter(this);
    }

    @Override
    public void start() {
        mWorkView.get().startLoading();
        getWorkData(new WorkRequest(mWorkView.get().getCategory(),
                WorkRemoteRepertory.IS_FIRST_TIME));
    }

    @Override
    public void clear() {
        if (mWorkView.get() != null) {
            mWorkView.clear();
        }
    }

    @Override
    public void getWorkData(WorkRequest workRequest) {
        mWorkModel.getWorkData(workRequest, new BaseModel.OnRequestResponse<WorkDatas>() {
            @Override
            public void onSuccess(WorkDatas workDatas) {
                if (mWorkView.get().isActive()) {
                    mWorkView.get().setWorkData(workDatas);
                    mWorkView.get().dimissLoading();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mWorkView.get().isActive()) {
                    mWorkView.get().showToast("数据加载错误");
                    mWorkView.get().dimissLoading();
                }
            }
        });
    }
}
