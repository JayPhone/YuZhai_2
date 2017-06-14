package com.yuzhai.yuzhaiwork_2.home_page.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.home_page.bean.HomePageData;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

/**
 * Created by 35429 on 2017/6/6.
 */

public interface HomePageContact {
    interface View extends BaseView<Presenter> {
        void setHomePageData(HomePageData homePageData);

        void setAutoLoginState(boolean loginState);

        void sendLoginRequest();

        void setLoginResponseData(LoginResponse loginResponseData);

        void finishActivity();
    }

    interface Presenter extends BasePresenter {
        void getHomePageData();

        void sendLoginRequest();

    }
}
