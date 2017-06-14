package com.yuzhai.yuzhaiwork_2.order_detail.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.WorkDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.ApplyOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.WorkDetailRequest;

import java.util.List;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface WorkDetailContact {
    interface View extends BaseView<Presenter> {
        void setWorkDetailData(WorkDetailResponse workDetailData);

        String getOrderId();

        void showProgressDialog(String msg);

        void hideProgressDialog();

        void setNeededImages(List<WorkDetailResponse.WorkDetail.Pictures> pictures);

        void setApplicantAvatars(List<WorkDetailResponse.WorkDetail.ApplicantAvatars> applicantAvatarses);

        void showApplyOrderDialog();

    }

    interface Presenter extends BasePresenter {
        void sendWorkDetailRequest(WorkDetailRequest workDetailRequest);

        void sentApplyOrderRequest(ApplyOrderRequest applyOrderRequest);
    }
}
