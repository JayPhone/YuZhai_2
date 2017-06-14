package com.yuzhai.yuzhaiwork_2.login_reg.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.util.RegexUtil;
import com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil;
import com.yuzhai.yuzhaiwork_2.login_reg.contact.LoginContact;
import com.yuzhai.yuzhaiwork_2.login_reg.model.ILoginModel;
import com.yuzhai.yuzhaiwork_2.login_reg.model.LoginModel;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

import java.lang.ref.WeakReference;

import static com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil.getSharePerference;

/**
 * Created by 35429 on 2017/5/18.
 */

public class LoginPresenter implements LoginContact.Presenter {
    private static final String TAG = "LoginPresenter";

    private WeakReference<LoginContact.View> mLoginView;
    private ILoginModel mLoginModel = new LoginModel();
    private Context mContext;

    public LoginPresenter(LoginContact.View loginView, Context context) {
        this.mLoginView = new WeakReference<>(loginView);
        //给View设置Presenter
        this.mLoginView.get().setPresenter(this);
        this.mContext = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void checkInput(LoginRequest loginRequest) {
        boolean phoneValidate;
        boolean pswValidate;
        if (loginRequest.getUserPhone() != null && loginRequest.getUserPsw() != null) {
            //校验手机号码
            Log.d(TAG, loginRequest.getUserPhone());
            if (loginRequest.getUserPhone().length() != 11) {
                mLoginView.get().showToast(mContext.getString(R.string.incorrect_phone));
            } else {
                //校验密码
                Log.d(TAG, loginRequest.getUserPsw());
                pswValidate = RegexUtil.regexPsw(loginRequest.getUserPsw());
                if (!pswValidate) {
                    mLoginView.get().showToast(mContext.getString(R.string.incorrect_psw));
                } else {
                    //发送登录请求
                    sentLoginRequest(loginRequest);
                }
            }
        }
    }

    @Override
    public void sentLoginRequest(final LoginRequest loginRequest) {
        mLoginModel.setLoginRequest(loginRequest, new LoginModel.OnRequestResponse<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                //判断Activity是否已经退出
                if (mLoginView.get().isActive()) {
                    switch (loginResponse.getCode()) {
                        case "1":
                            //设置已经登录
                            CustomApplication.getInstance().setIoginState(true);
                            mLoginView.get().setData(loginResponse);
                            //保存用户信息到本地
                            saveUserInfoToLocal(loginRequest);
                            mLoginView.get().showToast(mContext.getString(R.string.login_success));
                            break;
                        case "-1":
                            mLoginView.get().showToast(mContext.getString(R.string.psw_error));
                            break;
                        case "0":
                            mLoginView.get().showToast(mContext.getString(R.string.incorrect_account));
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mLoginView.get().isActive()) {
                    if (e instanceof HttpException) {
                        mLoginView.get().showToast(mContext.getString(R.string.server_error));
                    }
                }
            }
        });
    }

    @Override
    public void saveUserInfoToLocal(LoginRequest loginRequest) {
        SharedPreferences.Editor editor = SharePerferenceUtil
                .getSharePerference(CustomApplication.getInstance().getApplicationContext()
                        , SharePerferenceUtil.FileName.USER_INFO).edit();
        editor.putString(SharePerferenceUtil.Key.USERNAME, loginRequest.getUserPhone());
        editor.putString(SharePerferenceUtil.Key.USERPSW, loginRequest.getUserPsw());
        editor.apply();
    }

    @Override
    public void clear() {
        if (mLoginView.get() != null) {
            mLoginView.clear();
        }
    }
}
