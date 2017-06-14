package com.yuzhai.yuzhaiwork_2.order_detail.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelAppliedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAppliedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAppliedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAppliedDetailRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAppliedDetailRemoteRepertory implements IOrderAppliedDetailModel {
    private static final String TAG = "OrderAppliedDetailRemot";

    @Override
    public void sendOrderAppliedDetailRequest(OrderAppliedDetailRequest orderAppliedDetailRequest,
                                              final OnRequestResponse<OrderAppliedDetailResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getOrderAppliedDetailService()
                .getWorkDetailData(orderAppliedDetailRequest.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderAppliedDetailResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull OrderAppliedDetailResponse orderAppliedDetailResponse) {
                        Log.d(TAG, orderAppliedDetailResponse.toString());
                        onRequestResponse.onSuccess(orderAppliedDetailResponse);
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
    public void sendCancelAppliedOrderRequest(CancelAppliedOrderRequest cancelAppliedOrderRequest,
                                              final OnRequestResponse<CancelAppliedOrderResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getCancelAppliedOrderService()
                .cancelAppliedOrder(cancelAppliedOrderRequest.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CancelAppliedOrderResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CancelAppliedOrderResponse cancelAppliedOrderResponse) {
                        Log.d(TAG, cancelAppliedOrderResponse.toString());
                        onRequestResponse.onSuccess(cancelAppliedOrderResponse);
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
