package com.yuzhai.yuzhaiwork_2.order_detail.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelAcceptedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAcceptedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.WorkDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAcceptedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAcceptedDetailRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAcceptedDetailRemoteRepertory implements IOrderAcceptedDetailModel {
    @Override
    public void sendOrderAcceptedDetailRequest(OrderAcceptedDetailRequest orderAcceptedDetailRequest, final OnRequestResponse<OrderAcceptedDetailResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getOrderAcceptedDetailService()
                .getWorkDetailData(orderAcceptedDetailRequest.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderAcceptedDetailResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull OrderAcceptedDetailResponse orderAcceptedDetailResponse) {
                        Log.d(TAG, orderAcceptedDetailResponse.toString());
                        onRequestResponse.onSuccess(orderAcceptedDetailResponse);
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
    public void sendCancelAcceptedOrderRequest(CancelAcceptedOrderRequest cancelAcceptedOrderRequest, final OnRequestResponse<CancelAcceptedOrderResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getCancelAcceptedOrderService()
                .cancelAcceptedOrder(cancelAcceptedOrderRequest.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CancelAcceptedOrderResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CancelAcceptedOrderResponse cancelAcceptedOrderResponse) {
                        Log.d(TAG, cancelAcceptedOrderResponse.toString());
                        onRequestResponse.onSuccess(cancelAcceptedOrderResponse);
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
