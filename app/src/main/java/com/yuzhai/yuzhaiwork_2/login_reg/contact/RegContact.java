package com.yuzhai.yuzhaiwork_2.login_reg.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;

/**
 * Created by 35429 on 2017/5/17.
 */

public interface RegContact {
    interface View extends BaseView<Presenter> {
        void setValidateBtnEnable(boolean isEnable);

        void startCountDown();

        void showProgressDialog(String msg);

        void hideProgressDialog();
    }

    interface Presenter extends BasePresenter {
        void checkValidateInput(ValidateRequest validateRequest);

        void checkRegInput(RegRequest regRequest);

        void sendValidateRequest(ValidateRequest validateRequest);

        void sendRegRequest(RegRequest regRequest);

        void sendValiadateConfirmRequest(RegRequest regRequest);
    }
}
