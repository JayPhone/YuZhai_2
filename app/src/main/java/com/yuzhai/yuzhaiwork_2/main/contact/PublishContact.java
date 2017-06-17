package com.yuzhai.yuzhaiwork_2.main.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.main.bean.PublishResponse;
import com.yuzhai.yuzhaiwork_2.main.request.PublishRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/5/23.
 */

public interface PublishContact {
    interface View extends BaseView<Presenter> {
        void setTypeSpinnerData(List<Map<String, String>> typeSpinnerData);

        void setLimitSpinnerData(List<Map<String, String>> limitSpinnerData);

        void checkCameraPermission();

        void showOnDenienCameraPermission();

        boolean isSelectImageNumOverflow();

        void showAddImageDialog();

        void showCamera();

        void showImagePick();

        void showSnackBar();

        void addImageIntoPreviewLayout(String imagePath);

        void showDeleteImageDialog(android.view.View view);

        void showProgressDialog(String msg);

        void hideProgressDialog();

        void clearInput();

        void setPublishResult(PublishResponse publishResponse);
    }

    interface Presenter extends BasePresenter {
        boolean checkPublishInput(PublishRequest publishRequest);

        void getTypeSpinnerData();

        void getLimitSpinnerData();

        void sendPublishOrderRequest(PublishRequest publishRequest);

        void compressImages(PublishRequest publishRequest);
    }
}
