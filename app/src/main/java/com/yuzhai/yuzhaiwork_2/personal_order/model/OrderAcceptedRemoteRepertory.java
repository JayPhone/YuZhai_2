package com.yuzhai.yuzhaiwork_2.personal_order.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAcceptedDatas;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/6/8.
 */

public class OrderAcceptedRemoteRepertory implements IOrderAcceptedModel {
    private static final String TAG = "OrderAcceptedRemoteRepe";
    public final static String IS_FIRST_TIME = "yes";
    public final static String NOT_FIRST_TIME = "no";

    @Override
    public void getAcceptedOrderData(String isFirst, final OnRequestResponse<OrderAcceptedDatas> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getAcceptedOrderService()
                .getAcceptedOrders(isFirst)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderAcceptedDatas>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull OrderAcceptedDatas orderAcceptedDatas) {
                        Log.d(TAG, orderAcceptedDatas.toString());
                        onRequestResponse.onSuccess(orderAcceptedDatas);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.getMessage());
                        onRequestResponse.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
