package com.yuzhai.yuzhaiwork_2.user_info.presenter;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.user_info.bean.ExitLoginResponse;
import com.yuzhai.yuzhaiwork_2.user_info.bean.ReNameResponse;
import com.yuzhai.yuzhaiwork_2.user_info.bean.UploadAvaterResponse;
import com.yuzhai.yuzhaiwork_2.user_info.contact.UserInfoManagerContact;
import com.yuzhai.yuzhaiwork_2.user_info.model.IUserInfoManagerModel;
import com.yuzhai.yuzhaiwork_2.user_info.model.UserInfoManagerRepertory;
import com.yuzhai.yuzhaiwork_2.user_info.request.ReNameRequest;
import com.yuzhai.yuzhaiwork_2.user_info.request.UploadAvaterRequest;
import com.yuzhai.yuzhaiwork_2.user_info.view.UserInfoManagerFragment;

import java.io.File;
import java.lang.ref.WeakReference;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by 35429 on 2017/6/8.
 */

public class UserInfoManagerPresenter implements UserInfoManagerContact.Presenter {
    private static final String TAG = "UserInfoManagerPresente";
    private WeakReference<UserInfoManagerContact.View> mUserInfoManagerView;
    private IUserInfoManagerModel mUserInfoManagerModel = new UserInfoManagerRepertory();

    public UserInfoManagerPresenter(UserInfoManagerFragment userInfoManagerFragment) {
        mUserInfoManagerView = new WeakReference<UserInfoManagerContact.View>(userInfoManagerFragment);
        mUserInfoManagerView.get().setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void clear() {
        if (mUserInfoManagerView.get() != null) {
            mUserInfoManagerView.clear();
        }
    }

    @Override
    public void sendLoginExitRequest() {
        mUserInfoManagerModel.sendExitLoginRequest(new BaseModel.OnRequestResponse<ExitLoginResponse>() {
            @Override
            public void onSuccess(ExitLoginResponse exitLoginResponse) {
                if (mUserInfoManagerView.get().isActive()) {
                    mUserInfoManagerView.get().exitLoginResponse(exitLoginResponse);
                    mUserInfoManagerView.get().showToast("退出成功");
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mUserInfoManagerView.get().isActive()) {
                    mUserInfoManagerView.get().showToast("服务器异常");
                }
            }
        });
    }

    @Override
    public boolean checkUploadAvater(UploadAvaterRequest uploadAvaterRequest) {
        if (uploadAvaterRequest != null) {
            if (uploadAvaterRequest.getImagePath().equals("")) {
                mUserInfoManagerView.get().showToast("图片路径不能为空");
                return false;
            } else {
                //压缩图片
                compressImage(uploadAvaterRequest);
                return true;
            }
        }

        return false;
    }

    @Override
    public void compressImage(UploadAvaterRequest uploadAvaterRequest) {
        Log.d(TAG, uploadAvaterRequest.toString());
        Luban.get(CustomApplication.getInstance().getApplicationContext())
                .load(new File(uploadAvaterRequest.getImagePath()))            //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        //显示进度对话框
                        mUserInfoManagerView.get().showProgressDialog("图片正在压缩，请稍后");
                    }

                    @Override
                    public void onSuccess(File file) {
                        //发送上传头像请求
                        mUserInfoManagerView.get().hideProgressDialog();
                        sendUploadAvaterRequest(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mUserInfoManagerView.get().showToast("压缩出错，请稍后重试");
                        mUserInfoManagerView.get().hideProgressDialog();
                    }
                }).launch();    //启动压缩
    }

    @Override
    public void sendUploadAvaterRequest(File file) {
        mUserInfoManagerView.get().showProgressDialog("正在上传头像");
        mUserInfoManagerModel.sendUploadAvaterRequest(file, new BaseModel.OnRequestResponse<UploadAvaterResponse>() {
            @Override
            public void onSuccess(UploadAvaterResponse uploadAvaterResponse) {
                if (mUserInfoManagerView.get().isActive()) {
                    mUserInfoManagerView.get().hideProgressDialog();
                    mUserInfoManagerView.get().showToast("头像上传成功");
                    mUserInfoManagerView.get().setAvaterData(uploadAvaterResponse);
                    Log.d(TAG, uploadAvaterResponse.toString());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mUserInfoManagerView.get().isActive()) {
                    mUserInfoManagerView.get().hideProgressDialog();
                    mUserInfoManagerView.get().showToast("服务器异常");
                }
            }
        });
    }

    @Override
    public void sendReNameRequest(ReNameRequest reNameRequest) {
        mUserInfoManagerView.get().showProgressDialog("正在修改，请稍后");
        mUserInfoManagerModel.sendReNameRequest(reNameRequest, new BaseModel.OnRequestResponse<ReNameResponse>() {
            @Override
            public void onSuccess(ReNameResponse reNameResponse) {
                if (mUserInfoManagerView.get().isActive()) {
                    if (reNameResponse.getCode().equals("1")) {
                        mUserInfoManagerView.get().showToast("修改用户名成功");
                        mUserInfoManagerView.get().setNewName();
                        mUserInfoManagerView.get().hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mUserInfoManagerView.get().isActive()) {
                    mUserInfoManagerView.get().showToast("服务器异常");
                    mUserInfoManagerView.get().hideProgressDialog();
                }
            }
        });
    }
}
