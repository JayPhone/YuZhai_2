package com.yuzhai.yuzhaiwork_2.category.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;
import com.yuzhai.yuzhaiwork_2.category.request.WorkRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by 35429 on 2017/6/8.
 */

public class WorkRemoteRepertory implements IWorkModel {
    public final static String IS_FIRST_TIME = "yes";
    public final static String NOT_FIRST_TIME = "no";

    @Override
    public void getWorkData(WorkRequest workRequest, final OnRequestResponse<WorkDatas> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getWorkDataService()
                .getWorkData(workRequest.getCategoryType(), workRequest.getIsFirst())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WorkDatas>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WorkDatas workDatas) {
                        Log.d(TAG, workDatas.toString());
                        onRequestResponse.onSuccess(workDatas);
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
