package com.yuzhai.yuzhaiwork_2.personal_order.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAcceptedDatas;
import com.yuzhai.yuzhaiwork_2.personal_order.contact.OrderAcceptedContact;
import com.yuzhai.yuzhaiwork_2.personal_order.contact.OrderAppliedContact;
import com.yuzhai.yuzhaiwork_2.personal_order.model.IOrderAcceptedModel;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderAcceptedLocalRepertory;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderAcceptedRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAcceptedFragment;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAppliedFragment;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderAcceptedPresenter implements OrderAcceptedContact.Presenter {
    private WeakReference<OrderAcceptedContact.View> mOrderAcceptedView;
    private IOrderAcceptedModel mOrderAcceptedModel = new OrderAcceptedRemoteRepertory();

    public OrderAcceptedPresenter(OrderAcceptedFragment orderAcceptedFragment) {
        this.mOrderAcceptedView = new WeakReference<OrderAcceptedContact.View>(orderAcceptedFragment);
        this.mOrderAcceptedView.get().setPresenter(this);
    }

    @Override
    public void start() {
        mOrderAcceptedView.get().startLoading();
        getAcceptedOrderData(OrderAcceptedRemoteRepertory.IS_FIRST_TIME);
    }

    @Override
    public void clear() {
        if (mOrderAcceptedView.get() != null) {
            mOrderAcceptedView.clear();
        }
    }

    @Override
    public void getAcceptedOrderData(String isFirst) {
        mOrderAcceptedModel.getAcceptedOrderData(isFirst, new BaseModel.OnRequestResponse<OrderAcceptedDatas>() {
            @Override
            public void onSuccess(OrderAcceptedDatas orderAcceptedDatas) {
                if (orderAcceptedDatas.getOrders() != null) {
                    if (mOrderAcceptedView.get().isActive()) {
                        mOrderAcceptedView.get().setAcceptedOrderData(orderAcceptedDatas);
                        mOrderAcceptedView.get().showToast("更新了" + orderAcceptedDatas.getOrders().size() + "条数据");
                    }
                } else {
                    mOrderAcceptedView.get().showToast("请先登录");
                }
                mOrderAcceptedView.get().dimissLoading();
            }

            @Override
            public void onFailure(Throwable e) {
                if (mOrderAcceptedView.get().isActive()) {
                    mOrderAcceptedView.get().showToast("数据加载错误");
                    mOrderAcceptedView.get().dimissLoading();
                }
            }
        });
    }
}
