package com.yuzhai.yuzhaiwork_2.user_data.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.user_data.bean.AgreeAcceptOrderResponse;
import com.yuzhai.yuzhaiwork_2.user_data.bean.UserDataResponse;
import com.yuzhai.yuzhaiwork_2.user_data.contact.UserDataContact;
import com.yuzhai.yuzhaiwork_2.user_data.model.IUserDataModel;
import com.yuzhai.yuzhaiwork_2.user_data.model.UserDataRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.user_data.request.AgreeAcceptOrderRequest;
import com.yuzhai.yuzhaiwork_2.user_data.request.UserDataRequest;
import com.yuzhai.yuzhaiwork_2.user_data.view.UserDataFragment;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/14.
 */

public class UserDataPresenter implements UserDataContact.Presenter {
    private WeakReference<UserDataContact.View> mUserDataView;
    private IUserDataModel mUserDataModel = new UserDataRemoteRepertory();

    public UserDataPresenter(UserDataFragment userDataFragment) {
        mUserDataView = new WeakReference<UserDataContact.View>(userDataFragment);
        mUserDataView.get().setPresenter(this);
    }

    @Override
    public void start() {
        mUserDataView.get().showProgressDialog("正在加载数据，请稍后");
        sendUserDataRequest(new UserDataRequest(mUserDataView.get().getAvatarUrl()));
    }

    @Override
    public void clear() {
        if (mUserDataView.get() != null) {
            mUserDataView.clear();
        }
    }

    @Override
    public void sendUserDataRequest(UserDataRequest userDataRequest) {
        mUserDataModel.sendUserDataRequest(userDataRequest, new BaseModel.OnRequestResponse<UserDataResponse>() {
            @Override
            public void onSuccess(UserDataResponse userDataResponse) {
                if (mUserDataView.get().isActive()) {
                    mUserDataView.get().setUserData(userDataResponse);
                    mUserDataView.get().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mUserDataView.get().isActive()) {
                    mUserDataView.get().showToast("服务器异常");
                    mUserDataView.get().hideProgressDialog();
                }
            }
        });
    }

    @Override
    public void sendAgreeAcceptOrderRequest(AgreeAcceptOrderRequest agreeAcceptOrderRequest) {
        mUserDataView.get().showProgressDialog("正在确认接收者，请稍后");
        mUserDataModel.sendAgreeAcceptOrderRequest(agreeAcceptOrderRequest, new BaseModel.OnRequestResponse<AgreeAcceptOrderResponse>() {
            @Override
            public void onSuccess(AgreeAcceptOrderResponse agreeAcceptOrderResponse) {
                if (mUserDataView.get().isActive()) {
                    if (agreeAcceptOrderResponse.getCode().equals("1")) {
                        mUserDataView.get().showToast("确认接收者成功");
                    }
                    mUserDataView.get().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mUserDataView.get().isActive()) {
                    mUserDataView.get().hideProgressDialog();
                    mUserDataView.get().showToast("服务器异常");
                }
            }
        });
    }
}
