package com.yuzhai.yuzhaiwork_2.user_data.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.user_data.bean.AgreeAcceptOrderResponse;
import com.yuzhai.yuzhaiwork_2.user_data.bean.UserDataResponse;
import com.yuzhai.yuzhaiwork_2.user_data.request.AgreeAcceptOrderRequest;
import com.yuzhai.yuzhaiwork_2.user_data.request.UserDataRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/6/14.
 */

public class UserDataRemoteRepertory implements IUserDataModel {
    private static final String TAG = "UserDataRemoteRepertory";

    @Override
    public void sendUserDataRequest(UserDataRequest userDataRequest, final OnRequestResponse<UserDataResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getUserDataService()
                .getUserData(userDataRequest.getAvatarUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDataResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UserDataResponse userDataResponse) {
                        Log.d(TAG, userDataResponse.toString());
                        onRequestResponse.onSuccess(userDataResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onRequestResponse.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void sendAgreeAcceptOrderRequest(AgreeAcceptOrderRequest agreeAcceptOrderRequest, final OnRequestResponse<AgreeAcceptOrderResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getAgreeAcceptOrderService()
                .agreeAcceptOrder(agreeAcceptOrderRequest.getOrderId(), agreeAcceptOrderRequest.getBidderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgreeAcceptOrderResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgreeAcceptOrderResponse agreeAcceptOrderResponse) {
                        Log.d(TAG, agreeAcceptOrderResponse.toString());
                        onRequestResponse.onSuccess(agreeAcceptOrderResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onRequestResponse.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
