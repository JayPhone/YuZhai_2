package com.yuzhai.yuzhaiwork_2.order_detail.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelPublishedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderPublishedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.contact.OrderPublishedDetailContact;
import com.yuzhai.yuzhaiwork_2.order_detail.model.IOrderPublishedDetailModel;
import com.yuzhai.yuzhaiwork_2.order_detail.model.OrderPublishedDetailRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelPublishedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderPublishedDetailRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderPublishedDetailActivity;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderPublishedDetailPresenter implements OrderPublishedDetailContact.Presenter {
    private WeakReference<OrderPublishedDetailContact.View> mOrderPublishedDtailView;
    private IOrderPublishedDetailModel orderPublishedDetailModel = new OrderPublishedDetailRemoteRepertory();

    public OrderPublishedDetailPresenter(OrderPublishedDetailActivity orderPublishedDetailActivity) {
        mOrderPublishedDtailView = new WeakReference<OrderPublishedDetailContact.View>(orderPublishedDetailActivity);
    }

    @Override
    public void start() {
        sendOrderPublishedDetailRequest(new OrderPublishedDetailRequest(
                mOrderPublishedDtailView.get().getOrderId()));
    }

    @Override
    public void clear() {
        if (mOrderPublishedDtailView.get() != null) {
            mOrderPublishedDtailView.clear();
        }
    }

    @Override
    public void sendOrderPublishedDetailRequest(OrderPublishedDetailRequest orderPublishedDetailRequest) {
        mOrderPublishedDtailView.get().showProgressDialog("正在加载数据，请稍后");
        orderPublishedDetailModel.sendOrderPublishedDetailRequest(orderPublishedDetailRequest, new BaseModel.OnRequestResponse<OrderPublishedDetailResponse>() {
            @Override
            public void onSuccess(OrderPublishedDetailResponse orderPublishedDetailResponse) {
                if (mOrderPublishedDtailView.get() != null) {
                    mOrderPublishedDtailView.get().setPublishDetailData(orderPublishedDetailResponse);
                    mOrderPublishedDtailView.get().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mOrderPublishedDtailView.get() != null) {
                    mOrderPublishedDtailView.get().hideProgressDialog();
                    mOrderPublishedDtailView.get().showToast("服务器异常");
                }
            }
        });
    }

    @Override
    public void sendCancelPublishedOrderRequest(CancelPublishedOrderRequest cancelPublishedOrderRequest) {
        mOrderPublishedDtailView.get().showProgressDialog("正在发送退单申请，请稍后");
        orderPublishedDetailModel.sendCancelPublishedOrderRequest(cancelPublishedOrderRequest, new BaseModel.OnRequestResponse<CancelPublishedOrderResponse>() {
            @Override
            public void onSuccess(CancelPublishedOrderResponse cancelPublishedOrderResponse) {
                if (mOrderPublishedDtailView.get() != null) {
                    if (cancelPublishedOrderResponse.getCode().equals("1")) {
                        mOrderPublishedDtailView.get().showToast("取消发布订单成功");
                    } else if (cancelPublishedOrderResponse.getCode().equals("-1")) {
                        mOrderPublishedDtailView.get().showToast("退单失败,请稍后再试");
                    }
                    mOrderPublishedDtailView.get().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mOrderPublishedDtailView.get() != null) {
                    mOrderPublishedDtailView.get().hideProgressDialog();
                    mOrderPublishedDtailView.get().showToast("服务器异常");
                }
            }
        });
    }
}
