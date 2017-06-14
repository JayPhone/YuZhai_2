package com.yuzhai.yuzhaiwork_2.order_detail.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelPublishedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderPublishedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.WorkDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelPublishedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderPublishedDetailRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderPublishedDetailRemoteRepertory implements IOrderPublishedDetailModel {
    private static final String TAG = "OrderPublishedDetailRem";

    @Override
    public void sendOrderPublishedDetailRequest(OrderPublishedDetailRequest orderPublishedDetailRequest,
                                                final OnRequestResponse<OrderPublishedDetailResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getOrderPublishedDetailService()
                .getWorkDetailData(orderPublishedDetailRequest.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderPublishedDetailResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull OrderPublishedDetailResponse orderPublishedDetailResponse) {
                        Log.d(TAG, orderPublishedDetailResponse.toString());
                        onRequestResponse.onSuccess(orderPublishedDetailResponse);
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
    public void sendCancelPublishedOrderRequest(final CancelPublishedOrderRequest cancelPublishedOrderRequest, final OnRequestResponse<CancelPublishedOrderResponse> onRequestResponse) {
        RetrofitUtil.getInstance().getCancelPublishedOrderService()
                .cancelPublishedOrder(cancelPublishedOrderRequest.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CancelPublishedOrderResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CancelPublishedOrderResponse cancelPublishedOrderResponse) {
                        Log.d(TAG, cancelPublishedOrderResponse.toString());
                        onRequestResponse.onSuccess(cancelPublishedOrderResponse);
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
