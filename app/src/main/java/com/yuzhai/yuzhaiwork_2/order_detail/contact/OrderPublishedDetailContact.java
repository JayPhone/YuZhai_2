package com.yuzhai.yuzhaiwork_2.order_detail.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelPublishedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderPublishedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelPublishedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderPublishedDetailRequest;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface OrderPublishedDetailContact {
    interface View extends BaseView<Presenter> {
        void setPublishDetailData(OrderPublishedDetailResponse orderPublishedDetailResponse);

        void showProgressDialog(String msg);

        void hideProgressDialog();

        String getOrderId();

        void showCancelPublishedOrderDialog();

        void showConfirmFinishDialog();

        void setOrderState(String state);
    }

    interface Presenter extends BasePresenter {
        void sendOrderPublishedDetailRequest(OrderPublishedDetailRequest orderPublishedDetailRequest);

        void sendCancelPublishedOrderRequest(CancelPublishedOrderRequest cancelPublishedOrderRequest);
    }
}
