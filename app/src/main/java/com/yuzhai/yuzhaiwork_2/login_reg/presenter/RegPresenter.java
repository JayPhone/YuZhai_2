package com.yuzhai.yuzhaiwork_2.login_reg.presenter;

import android.content.Context;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RegexUtil;
import com.yuzhai.yuzhaiwork_2.login_reg.contact.RegContact;
import com.yuzhai.yuzhaiwork_2.login_reg.model.IRegModel;
import com.yuzhai.yuzhaiwork_2.login_reg.model.RegModel;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegResponse;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateResponse;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/5/22.
 */

public class RegPresenter implements RegContact.Presenter {
    private static final String TAG = "RegPresenter";
    private WeakReference<RegContact.View> mRegView;
    private IRegModel mRegModel = new RegModel();
    private Context mContext;

    public RegPresenter(RegContact.View regView, Context context) {
        this.mRegView = new WeakReference<>(regView);
        //给View设置Presenter
        this.mRegView.get().setPresenter(this);
        this.mContext = context;
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
                mRegView.get().showToast(mContext.getString(R.string.incorrect_phone));
            } else {
                //发送获取验证码请求
                sendValidateRequest(validateRequest);
            }
        }
    }

    @Override
    public void checkRegInput(RegRequest regRequest) {
        boolean phoneValidate;
        boolean codeValidate;
        boolean pswValidate;
        if (regRequest.getUserPhone() != null && regRequest.getValidateCode() != null
                && regRequest.getUserPsw() != null && regRequest.getCfmPsw() != null) {
            //验证手机号码
            phoneValidate = RegexUtil.regexPhone(regRequest.getUserPhone());
            if (!phoneValidate) {
                mRegView.get().showToast(mContext.getString(R.string.incorrect_phone));
            } else {
                //验证验证码
                codeValidate = RegexUtil.regexValidateCode(regRequest.getValidateCode());
                if (!codeValidate) {
                    mRegView.get().showToast(mContext.getString(R.string.incorrect_validate_code));
                } else {
                    //验证密码
                    pswValidate = RegexUtil.regexPsw(regRequest.getUserPsw());
                    if (!pswValidate) {
                        mRegView.get().showToast(mContext.getString(R.string.incorrect_psw));
                        //验证两次密码是否一致
                    } else if (!regRequest.getUserPsw().equals(regRequest.getCfmPsw())) {
                        mRegView.get().showToast(mContext.getString(R.string.not_same_psw));
                    } else {
                        //校验验证码是否正确
                        sendValiadateConfirmRequest(regRequest);
                    }
                }
            }
        }
    }

    @Override
    public void sendValidateRequest(ValidateRequest validateRequest) {
        mRegModel.sendValidateRequest(validateRequest, new BaseModel.OnRequestResponse<ValidateResponse>() {
            @Override
            public void onSuccess(ValidateResponse validateResponse) {
                if (mRegView.get().isActive()) {
                    if (validateResponse.getCode().equals("1")) {
                        mRegView.get().startCountDown();
                        mRegView.get().showToast("验证码已发送，请注意查收");
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mRegView.get().isActive()) {
                    mRegView.get().setValidateBtnEnable(true);
                    mRegView.get().showToast("服务器异常，请稍后再试");
                }
            }
        });
    }

    @Override
    public void sendRegRequest(RegRequest regRequest) {
        mRegModel.sendRegRequest(regRequest, new BaseModel.OnRequestResponse<RegResponse>() {
            @Override
            public void onSuccess(RegResponse regResponse) {
                if (mRegView.get().isActive()) {
                    mRegView.get().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void sendValiadateConfirmRequest(final RegRequest regRequest) {
        mRegModel.sendValiadateConfirmRequest(regRequest, new BaseModel.OnRequestResponse<ValidateResponse>() {
            @Override
            public void onSuccess(ValidateResponse validateResponse) {
                if (mRegView.get().isActive()) {
                    if (validateResponse.getCode().equals("1")) {
                        mRegView.get().showToast("验证通过");
                        //发送注册请求
                        sendRegRequest(regRequest);
                        mRegView.get().showProgressDialog("正在注册");
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mRegView.get().isActive()) {
                    mRegView.get().showToast("验证码错误，请重新输入");
                }
            }
        });
    }

    @Override
    public void clear() {
        if (mRegView.get() != null) {
            mRegView.clear();
        }
    }
}
