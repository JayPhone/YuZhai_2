package com.yuzhai.yuzhaiwork_2.order_detail.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAcceptedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAcceptedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAcceptedDetailRequest;

/**
 * Created by 35429 on 2017/6/13.
 */

public interface OrderAcceptedDetailContact {
    interface View extends BaseView<Presenter> {
        void setAcceptedDetailData(OrderAcceptedDetailResponse orderAcceptedDetailResponse);

        void showProgressDialog(String msg);

        void hideProgressDialog();

        String getOrderId();

        void showCancelAcceptedOrderDialog();

        void setPublishName(String publishName);

        void setPublishAvater(String avatarUrl);
    }

    interface Presenter extends BasePresenter {
        void sendOrderAcceptedDetailRequest(OrderAcceptedDetailRequest orderAcceptedDetailRequest);

        void sendCancelAcceptedOrderRequest(CancelAcceptedOrderRequest cancelAcceptedOrderRequest);
    }
}
