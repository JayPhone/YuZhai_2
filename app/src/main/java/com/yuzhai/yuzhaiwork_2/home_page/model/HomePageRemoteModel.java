package com.yuzhai.yuzhaiwork_2.home_page.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil;
import com.yuzhai.yuzhaiwork_2.home_page.bean.HomePageData;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/6/6.
 */

public class HomePageRemoteModel implements IHomePageModel {
    private static final String TAG = "HomePageRemoteModel";

    @Override
    public void getHomePageData(OnRequestResponse<HomePageData> onRequestResponse) {
        onRequestResponse.onSuccess(new HomePageData(R.drawable.welcome_logo));
    }

    @Override
    public void sentLoginRequest(LoginRequest loginRequest, final OnRequestResponse<LoginResponse> onRequestResponse) {
        //清空Cookie
        SharePerferenceUtil
                .getSharePerference(CustomApplication.getInstance().getApplicationContext(),
                        SharePerferenceUtil.FileName.COOKIE)
                .edit()
                .putString("cookie", "")
                .apply();
        RetrofitUtil.getInstance().getLoginService()
                .login(loginRequest.getUserPhone(), loginRequest.getUserPsw(), loginRequest.getRegID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull LoginResponse loginResponse) {
                        Log.d(TAG, "next");
                        if (loginResponse != null) {
                            onRequestResponse.onSuccess(loginResponse);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "login_error");
                        onRequestResponse.onFailure(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "login_complete");
                    }
                });
    }
}
