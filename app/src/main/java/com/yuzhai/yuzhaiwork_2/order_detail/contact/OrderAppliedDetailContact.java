package com.yuzhai.yuzhaiwork_2.order_detail.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAppliedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAppliedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAppliedDetailRequest;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface OrderAppliedDetailContact {
    interface View extends BaseView<Presenter> {
        void setAppliedDetailData(OrderAppliedDetailResponse orderAppliedDetailResponse);

        void showProgressDialog(String msg);

        void hideProgressDialog();

        String getOrderId();

        void showCancelAppliedOrderDialog();

        void setPublishName(String publishName);

        void setPublishAvater(String avatarUrl);
    }

    interface Presenter extends BasePresenter {
        void sendOrderAppliedDetailRequest(OrderAppliedDetailRequest orderAppliedDetailRequest);

        void sendCancelAppliedOrderRequest(CancelAppliedOrderRequest cancelAppliedOrderRequest);
    }
}
