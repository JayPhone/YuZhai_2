package com.yuzhai.yuzhaiwork_2.login_reg.presenter;

import android.content.Context;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RegexUtil;
import com.yuzhai.yuzhaiwork_2.login_reg.contact.ForgetPswContact;
import com.yuzhai.yuzhaiwork_2.login_reg.model.ForgetPswModel;
import com.yuzhai.yuzhaiwork_2.login_reg.model.IForgetPswModel;
import com.yuzhai.yuzhaiwork_2.login_reg.request.AlterPswRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.AlterPswResponse;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateResponse;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/5/24.
 */

public class ForgetPswPresnter implements ForgetPswContact.Presenter {
    private static final String TAG = "ForgetPswPresnter";
    private WeakReference<ForgetPswContact.View> mForgetPswView;
    private IForgetPswModel mForgetPswModel = new ForgetPswModel();
    private Context mContext;

    public ForgetPswPresnter(ForgetPswContact.View forgetPswView, Context mContext) {
        this.mForgetPswView = new WeakReference<>(forgetPswView);
        this.mForgetPswView.get().setPresenter(this);
        this.mContext = mContext;
    }

    @Override
    public void start() {

    }

    @Override
    public void checkValidateInput(ValidateRequest validateRequest) {
        boolean phoneValidate;
        if (validateRequest.getUserPhone() != null && validateRequest.getValidateCode() != null) {
            //校验手机
            phoneValidate = RegexUtil.regexPhone(validateRequest.getUserPhone());
            if (!phoneValidate) {
                mForgetPswView.get().showToast(mContext.getString(R.string.incorrect_phone));
            } else {
                //发送获取验证码请求
                sendValidateRequest(validateRequest);
            }
        }
    }

    @Override
    public void checkAlterPswInput(AlterPswRequest alterPswRequest) {
        boolean phoneValidate;
        boolean codeValidate;
        boolean pswValidate;
        if (alterPswRequest.getUserPhone() != null && alterPswRequest.getValidateCode() != null
                && alterPswRequest.getPsw1() != null && alterPswRequest.getPsw2() != null) {
            //验证手机号码
            phoneValidate = RegexUtil.regexPhone(alterPswRequest.getUserPhone());
            if (!phoneValidate) {
                mForgetPswView.get().showToast(mContext.getString(R.string.incorrect_phone));
            } else {
                //验证验证码
                codeValidate = RegexUtil.regexValidateCode(alterPswRequest.getValidateCode());
                if (!codeValidate) {
                    mForgetPswView.get().showToast(mContext.getString(R.string.incorrect_validate_code));
                } else {
                    //验证密码
                    pswValidate = RegexUtil.regexPsw(alterPswRequest.getPsw1());
                    if (!pswValidate) {
                        mForgetPswView.get().showToast(mContext.getString(R.string.incorrect_psw));
                        //验证两次密码是否一致
                    } else if (!alterPswRequest.getPsw1().equals(alterPswRequest.getPsw2())) {
                        mForgetPswView.get().showToast(mContext.getString(R.string.not_same_psw));
                    } else {
                        sendAlterPswRequest(alterPswRequest);
                    }
                }
            }
        }
    }

    @Override
    public void sendValidateRequest(ValidateRequest validateRequest) {
        mForgetPswModel.sendValidateRequest(validateRequest, new BaseModel.OnRequestResponse<ValidateResponse>() {
            @Override
            public void onSuccess(ValidateResponse validateResponse) {

            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void sendAlterPswRequest(AlterPswRequest alterPswRequest) {
        mForgetPswModel.sendAlterRequest(alterPswRequest, new BaseModel.OnRequestResponse<AlterPswResponse>() {
            @Override
            public void onSuccess(AlterPswResponse alterPswResponse) {

            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void clear() {
        if (mForgetPswView.get() != null) {
            mForgetPswView.clear();
        }
    }
}
