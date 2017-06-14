package com.yuzhai.yuzhaiwork_2.login_reg.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.login_reg.request.AlterPswRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;

/**
 * Created by 35429 on 2017/5/24.
 */

public interface ForgetPswContact {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        void checkValidateInput(ValidateRequest validateRequest);

        void checkAlterPswInput(AlterPswRequest alterPswRequest);

        void sendValidateRequest(ValidateRequest validateRequest);

        void sendAlterPswRequest(AlterPswRequest alterPswRequest);
    }
}
