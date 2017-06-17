package com.yuzhai.yuzhaiwork_2.personal_order.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAppliedDatas;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/6/8.
 */

public class OrderAppliedRemoteRepertory implements IOrderAppliedModel {
    private static final String TAG = "OrderAppliedRemoteReper";
    public final static String IS_FIRST_TIME = "yes";
    public final static String NOT_FIRST_TIME = "no";

    @Override
    public void getAppliedOrderData(String isFirst, final OnRequestResponse<OrderAppliedDatas> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getAppliedOrderService()
                .getAppliedOrders(isFirst)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderAppliedDatas>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull OrderAppliedDatas orderAppliedDatas) {
                        Log.d(TAG, orderAppliedDatas.toString());
                        onRequestResponse.onSuccess(orderAppliedDatas);
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
