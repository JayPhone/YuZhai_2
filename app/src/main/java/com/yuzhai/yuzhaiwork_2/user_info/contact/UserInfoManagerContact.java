package com.yuzhai.yuzhaiwork_2.user_info.contact;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.user_info.bean.ExitLoginResponse;
import com.yuzhai.yuzhaiwork_2.user_info.bean.UploadAvaterResponse;
import com.yuzhai.yuzhaiwork_2.user_info.request.ReNameRequest;
import com.yuzhai.yuzhaiwork_2.user_info.request.UploadAvaterRequest;

import java.io.File;

/**
 * Created by 35429 on 2017/6/8.
 */

public interface UserInfoManagerContact {
    interface View extends BaseView<Presenter> {
        void setInitData();

        void exitLogin();

        void exitLoginResponse(ExitLoginResponse exitLoginResponse);

        void showAddImageDialog();

        void checkCameraPermission();

        void showOnDenienCameraPermission();

        void showCamera();

        void showImagePick();

        void showProgressDialog(String msg);

        void hideProgressDialog();

        void setAvaterData(UploadAvaterResponse uploadAvaterResponse);

        void setAvatar(String avatarUrl);

        void showAlterUserNameDialog();

        void setNewName();

        void notifyUserNameAlter();

        void notifyUserAvatarAlter(String avatarUrl);
    }

    interface Presenter extends BasePresenter {
        void sendLoginExitRequest();

        boolean checkUploadAvater(UploadAvaterRequest uploadAvaterRequest);

        void compressImage(UploadAvaterRequest uploadAvaterRequest);

        void sendUploadAvaterRequest(File file);

        void sendReNameRequest(ReNameRequest reNameRequest);
    }
}
