package com.yuzhai.yuzhaiwork_2.login_reg.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.login_reg.request.AlterPswRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.AlterPswResponse;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/5/24.
 */

public class ForgetPswModel implements IForgetPswModel {
    private static final String TAG = "ForgetPswModel";

    @Override
    public void sendValidateRequest(ValidateRequest validateRequest, OnRequestResponse<ValidateResponse> onRequestResponse) {

    }

    @Override
    public void sendAlterRequest(AlterPswRequest alterPswRequest, final OnRequestResponse<AlterPswResponse> onRequestResponse) {
        RetrofitUtil.getInstance().getAlterPswService()
                .alterPsw(alterPswRequest.getUserPhone(), alterPswRequest.getPsw1(), alterPswRequest.getPsw2())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AlterPswResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AlterPswResponse alterPswResponse) {
                        Log.d(TAG, "next");
                        if (alterPswResponse != null) {
                            onRequestResponse.onSuccess(alterPswResponse);
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
}
