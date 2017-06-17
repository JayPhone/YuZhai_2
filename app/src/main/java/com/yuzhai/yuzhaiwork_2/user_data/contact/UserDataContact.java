package com.yuzhai.yuzhaiwork_2.user_data.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.user_data.bean.UserDataResponse;
import com.yuzhai.yuzhaiwork_2.user_data.request.AgreeAcceptOrderRequest;
import com.yuzhai.yuzhaiwork_2.user_data.request.UserDataRequest;

/**
 * Created by 35429 on 2017/6/14.
 */

public interface UserDataContact {
    interface View extends BaseView<Presenter> {
        String getAvatarUrl();

        String getPrice();

        String getOrderId();

        void showAgreeAcceptDialog();

        void setUserData(UserDataResponse userDataResponse);

        void showProgressDialog(String msg);

        void hideProgressDialog();
    }

    interface Presenter extends BasePresenter {
        void sendUserDataRequest(UserDataRequest userDataRequest);

        void sendAgreeAcceptOrderRequest(AgreeAcceptOrderRequest agreeAcceptOrderRequest);
    }
}
