package com.yuzhai.yuzhaiwork_2.user_info.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.user_info.bean.ExitLoginResponse;
import com.yuzhai.yuzhaiwork_2.user_info.bean.ReNameResponse;
import com.yuzhai.yuzhaiwork_2.user_info.bean.UploadAvaterResponse;
import com.yuzhai.yuzhaiwork_2.user_info.request.ReNameRequest;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 35429 on 2017/6/9.
 */

public class UserInfoManagerRepertory implements IUserInfoManagerModel {
    private static final String TAG = "UserInfoManagerRepertor";

    @Override
    public void sendExitLoginRequest(final OnRequestResponse<ExitLoginResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getExitLoginService()
                .exitLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExitLoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ExitLoginResponse exitLoginResponse) {
                        Log.d(TAG, exitLoginResponse.toString());
                        onRequestResponse.onSuccess(exitLoginResponse);
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

    @Override
    public void sendUploadAvaterRequest(File file, final OnRequestResponse<UploadAvaterResponse> onRequestResponse) {
        RequestBody requestBody = RetrofitUtil.MediaTypeImageJpeg(file);
        RetrofitUtil.getInstance()
                .getUploadAvaterService()
                .uploadAvater(MultipartBody.Part.createFormData("file", file.getName(), requestBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadAvaterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UploadAvaterResponse uploadAvaterResponse) {
                        onRequestResponse.onSuccess(uploadAvaterResponse);
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
    public void sendReNameRequest(ReNameRequest reNameRequest, final OnRequestResponse<ReNameResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getReNameService()
                .reName(reNameRequest.getNewName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReNameResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ReNameResponse reNameResponse) {
                        onRequestResponse.onSuccess(reNameResponse);
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
