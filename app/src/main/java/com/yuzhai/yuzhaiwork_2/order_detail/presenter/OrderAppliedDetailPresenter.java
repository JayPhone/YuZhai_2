package com.yuzhai.yuzhaiwork_2.order_detail.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.CancelAppliedOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAppliedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.contact.OrderAppliedDetailContact;
import com.yuzhai.yuzhaiwork_2.order_detail.model.IOrderAppliedDetailModel;
import com.yuzhai.yuzhaiwork_2.order_detail.model.OrderAppliedDetailRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAppliedOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAppliedDetailRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderAppliedDetailActivity;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAppliedDetailPresenter implements OrderAppliedDetailContact.Presenter {
    private WeakReference<OrderAppliedDetailContact.View> mOrderAppliedDetailView;
    private IOrderAppliedDetailModel morderAppliedDetailModel = new OrderAppliedDetailRemoteRepertory();

    public OrderAppliedDetailPresenter(OrderAppliedDetailActivity orderAppliedDetailActivity) {
        mOrderAppliedDetailView = new WeakReference<OrderAppliedDetailContact.View>(orderAppliedDetailActivity);
    }

    @Override
    public void start() {
        sendOrderAppliedDetailRequest(new OrderAppliedDetailRequest(
                mOrderAppliedDetailView.get().getOrderId()));
    }

    @Override
    public void clear() {
        if (mOrderAppliedDetailView.get() != null) {
            mOrderAppliedDetailView.clear();
        }
    }

    @Override
    public void sendOrderAppliedDetailRequest(final OrderAppliedDetailRequest orderAppliedDetailRequest) {
        mOrderAppliedDetailView.get().showProgressDialog("正在加载数据，请稍后");
        morderAppliedDetailModel.sendOrderAppliedDetailRequest(orderAppliedDetailRequest,
                new BaseModel.OnRequestResponse<OrderAppliedDetailResponse>() {
                    @Override
                    public void onSuccess(OrderAppliedDetailResponse orderAppliedDetailResponse) {
                        if (mOrderAppliedDetailView.get() != null) {
                            mOrderAppliedDetailView.get().setAppliedDetailData(orderAppliedDetailResponse);
                            mOrderAppliedDetailView.get().hideProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        if (mOrderAppliedDetailView.get() != null) {
                            mOrderAppliedDetailView.get().hideProgressDialog();
                            mOrderAppliedDetailView.get().showToast("服务器异常");
                        }
                    }
                });
    }

    @Override
    public void sendCancelAppliedOrderRequest(CancelAppliedOrderRequest cancelAppliedOrderRequest) {
        mOrderAppliedDetailView.get().showProgressDialog("正在取消申请订单，请稍后");
        morderAppliedDetailModel.sendCancelAppliedOrderRequest(cancelAppliedOrderRequest,
                new BaseModel.OnRequestResponse<CancelAppliedOrderResponse>() {
                    @Override
                    public void onSuccess(CancelAppliedOrderResponse cancelAppliedOrderResponse) {
                        if (mOrderAppliedDetailView.get() != null) {
                            mOrderAppliedDetailView.get().hideProgressDialog();
                            if (cancelAppliedOrderResponse.getCode().equals("1")) {
                                mOrderAppliedDetailView.get().showToast("取消申请订单成功");
                            } else if (cancelAppliedOrderResponse.getCode().equals("-1")) {
                                mOrderAppliedDetailView.get().showToast("退单失败,请稍后再试");
                            }
                            mOrderAppliedDetailView.get().hideProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        if (mOrderAppliedDetailView.get() != null) {
                            mOrderAppliedDetailView.get().hideProgressDialog();
                            mOrderAppliedDetailView.get().showToast("服务器异常");
                        }
                    }
                });
    }
}
