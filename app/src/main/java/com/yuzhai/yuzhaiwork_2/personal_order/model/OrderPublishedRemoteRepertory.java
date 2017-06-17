package com.yuzhai.yuzhaiwork_2.personal_order.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderPublishedDatas;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderPublishedRemoteRepertory implements IOrderPublishedModel {
    private static final String TAG = "OrderPublishedRemoteRep";
    public final static String IS_FIRST_TIME = "yes";
    public final static String NOT_FIRST_TIME = "no";

    @Override
    public void getPublishedOrderData(String isFirst, final OnRequestResponse<OrderPublishedDatas> onRequestResponse) {
        RetrofitUtil.getInstance().getPublishedOrderService()
                .getPublishedOrders(isFirst)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderPublishedDatas>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull OrderPublishedDatas orderPublishedDatas) {
                        Log.d(TAG, orderPublishedDatas.toString());
                        onRequestResponse.onSuccess(orderPublishedDatas);
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
