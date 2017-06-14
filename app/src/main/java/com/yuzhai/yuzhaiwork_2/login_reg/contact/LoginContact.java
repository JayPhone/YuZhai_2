package com.yuzhai.yuzhaiwork_2.login_reg.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

/**
 * Created by 35429 on 2017/5/17.
 */

public interface LoginContact {
    interface View extends BaseView<Presenter> {
        void setData(LoginResponse loginResponse);
    }

    interface Presenter extends BasePresenter {
        void checkInput(LoginRequest loginRequest);

        void sentLoginRequest(LoginRequest loginRequest);

        void saveUserInfoToLocal(LoginRequest loginRequest);
    }
}
