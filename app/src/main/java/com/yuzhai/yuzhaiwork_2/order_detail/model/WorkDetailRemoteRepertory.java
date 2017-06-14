package com.yuzhai.yuzhaiwork_2.order_detail.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.ApplyOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.WorkDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.ApplyOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.WorkDetailRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/6/13.
 */

public class WorkDetailRemoteRepertory implements IWorkDetailModel {
    private static final String TAG = "WorkDetailRemoteReperto";

    @Override
    public void sendWorkDetailRequest(WorkDetailRequest workDetailRequest, final OnRequestResponse<WorkDetailResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getWorkDetailService()
                .getWorkDetailData(workDetailRequest.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WorkDetailResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WorkDetailResponse workDetailResponse) {
                        Log.d(TAG, workDetailResponse.toString());
                        onRequestResponse.onSuccess(workDetailResponse);
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
    public void sendApplyOrderRequest(ApplyOrderRequest applyOrderRequest, final OnRequestResponse<ApplyOrderResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getApplyOrderService()
                .applyOrder(applyOrderRequest.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApplyOrderResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApplyOrderResponse applyOrderResponse) {
                        Log.d(TAG, applyOrderResponse.toString());
                        onRequestResponse.onSuccess(applyOrderResponse);
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
