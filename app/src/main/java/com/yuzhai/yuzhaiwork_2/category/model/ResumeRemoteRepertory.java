package com.yuzhai.yuzhaiwork_2.category.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.category.bean.ResumeDatas;
import com.yuzhai.yuzhaiwork_2.category.request.ResumeRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 35429 on 2017/6/9.
 */

public class ResumeRemoteRepertory implements IResumeModel {
    private static final String TAG = "ResumeRemoteRepertory";
    public final static String IS_FIRST_TIME = "yes";
    public final static String NOT_FIRST_TIME = "no";

    @Override
    public void getResumeData(ResumeRequest resumeRequest, final OnRequestResponse<ResumeDatas> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getResumeService()
                .getResumeData(resumeRequest.getCategoryType(), resumeRequest.getIsFirst())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResumeDatas>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResumeDatas resumeDatas) {
                        Log.d(TAG, resumeDatas.toString());
                        onRequestResponse.onSuccess(resumeDatas);
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
