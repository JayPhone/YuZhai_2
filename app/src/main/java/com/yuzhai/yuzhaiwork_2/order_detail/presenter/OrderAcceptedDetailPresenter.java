package com.yuzhai.yuzhaiwork_2.order_detail.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelAcceptedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAcceptedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.contact.OrderAcceptedDetailContact;
import com.yuzhai.yuzhaiwork_2.order_detail.model.IOrderAcceptedDetailModel;
import com.yuzhai.yuzhaiwork_2.order_detail.model.OrderAcceptedDetailRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAcceptedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAcceptedDetailRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderAcceptedDetailActivity;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAcceptedDetailPresenter implements OrderAcceptedDetailContact.Presenter {
    private WeakReference<OrderAcceptedDetailContact.View> mOrderAcceptedDetailView;
    private IOrderAcceptedDetailModel mOrderAcceptedModel = new OrderAcceptedDetailRemoteRepertory();

    public OrderAcceptedDetailPresenter(OrderAcceptedDetailActivity orderAcceptedDetailActivity) {
        mOrderAcceptedDetailView = new WeakReference<OrderAcceptedDetailContact.View>(orderAcceptedDetailActivity);
    }

    @Override
    public void start() {
        sendOrderAcceptedDetailRequest(new OrderAcceptedDetailRequest(
                mOrderAcceptedDetailView.get().getOrderId()));
    }

    @Override
    public void clear() {
        if (mOrderAcceptedDetailView.get() != null) {
            mOrderAcceptedDetailView.clear();
        }
    }

    @Override
    public void sendOrderAcceptedDetailRequest(OrderAcceptedDetailRequest orderAcceptedDetailRequest) {
        mOrderAcceptedDetailView.get().showProgressDialog("正在加载数据，请稍后");
        mOrderAcceptedModel.sendOrderAcceptedDetailRequest(orderAcceptedDetailRequest,
                new BaseModel.OnRequestResponse<OrderAcceptedDetailResponse>() {
                    @Override
                    public void onSuccess(OrderAcceptedDetailResponse orderAcceptedDetailResponse) {
                        if (mOrderAcceptedDetailView.get() != null) {
                            mOrderAcceptedDetailView.get().setAcceptedDetailData(orderAcceptedDetailResponse);
                            mOrderAcceptedDetailView.get().hideProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        if (mOrderAcceptedDetailView.get() != null) {
                            mOrderAcceptedDetailView.get().hideProgressDialog();
                            mOrderAcceptedDetailView.get().showToast("服务器异常");
                        }
                    }
                });
    }

    @Override
    public void sendCancelAcceptedOrderRequest(CancelAcceptedOrderRequest cancelAcceptedOrderRequest) {
        mOrderAcceptedDetailView.get().showProgressDialog("正在取消接收订单,请稍后");
        mOrderAcceptedModel.sendCancelAcceptedOrderRequest(cancelAcceptedOrderRequest,
                new BaseModel.OnRequestResponse<CancelAcceptedOrderResponse>() {
                    @Override
                    public void onSuccess(CancelAcceptedOrderResponse cancelAcceptedOrderResponse) {
                        if (mOrderAcceptedDetailView.get() != null) {
                            if (cancelAcceptedOrderResponse.getCode().equals("1")) {
                                mOrderAcceptedDetailView.get().showToast("取消接收订单成功");
                            } else if (cancelAcceptedOrderResponse.getCode().equals("-1")) {
                                mOrderAcceptedDetailView.get().showToast("退单失败,请稍后再试");
                            }
                            mOrderAcceptedDetailView.get().hideProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        if (mOrderAcceptedDetailView.get() != null) {
                            mOrderAcceptedDetailView.get().hideProgressDialog();
                            mOrderAcceptedDetailView.get().showToast("服务器异常");
                        }
                    }
                });
    }
}
