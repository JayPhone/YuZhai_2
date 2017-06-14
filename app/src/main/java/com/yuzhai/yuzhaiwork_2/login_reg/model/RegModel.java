package com.yuzhai.yuzhaiwork_2.login_reg.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.base.view.UnRepeatToast;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegResponse;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateResponse;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by 35429 on 2017/5/22.
 */

public class RegModel implements IRegModel {
    @Override
    public void sendValidateRequest(ValidateRequest validateRequest, final OnRequestResponse<ValidateResponse> onRequestResponse) {
        BmobSMS.requestSMSCode(CustomApplication.getInstance().getApplicationContext(), validateRequest.getUserPhone(), "御宅工作", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {//验证码发送成功
                    onRequestResponse.onSuccess(new ValidateResponse("1"));
                    Log.i(TAG, "短信id：" + integer);
                } else {
                    onRequestResponse.onFailure(e);
                }
            }
        });
    }

    @Override
    public void sendRegRequest(RegRequest regRequest, final OnRequestResponse<RegResponse> onRequestResponse) {
        RetrofitUtil.getInstance().getRegService()
                .register(regRequest.getUserPhone(), regRequest.getUserPsw())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegResponse regResponse) {
                        Log.d(TAG, "next");
                        if (regResponse != null) {
                            onRequestResponse.onSuccess(regResponse);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "error");
                        onRequestResponse.onFailure(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "complete");
                    }
                });
    }

    @Override
    public void sendValiadateConfirmRequest(RegRequest regRequest, final OnRequestResponse<ValidateResponse> onRequestResponse) {
        //发送验证验证码请求
        BmobSMS.verifySmsCode(CustomApplication.getInstance().getApplicationContext(),
                regRequest.getUserPhone(), regRequest.getValidateCode(), new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException ex) {
                        if (ex == null) {//短信验证码已验证成功
                            onRequestResponse.onSuccess(new ValidateResponse("1"));
                        } else {
                            onRequestResponse.onFailure(ex);
                        }
                    }
                });
    }
}
